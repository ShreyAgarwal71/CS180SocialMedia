package com.lewall.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Request.EHeader;
import com.lewall.api.Request.EMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Connection {
    private static final Logger logger = LogManager.getLogger(Connection.class);

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private static SocketChannel serverChannel;

    private static ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    private static Selector selector;
    private static HashMap<UUID, LinkedBlockingQueue<String>> responseQueues = new HashMap<>();

    public static synchronized boolean connect() {
        if (serverChannel != null && serverChannel.isConnected()) {
            return true;
        }

        try {
            selector = Selector.open();
            serverChannel = SocketChannel.open();
            serverChannel.connect(new InetSocketAddress(HOST, PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_READ);

            logger.info("Connected to Server");

            executor.submit(() -> {
                while (true) {
                    try {
                        if (!handleResponse()) {
                            break;
                        }
                    } catch (IOException e) {
                        logger.error(e);
                        break;
                    } catch (InterruptedException e) {
                        logger.error(e);
                        break;
                    }
                }

                handleClose();
            });
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private synchronized static void handleClose() {
        try {
            serverChannel.close();
            selector.close();

            logger.info("Closed Connection");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private static boolean isConnectionActive() {
        if (serverChannel == null || !serverChannel.isConnected()) {
            logger.debug("Attempting Reconnection");
            return connect();
        }

        return serverChannel.isConnected();
    }

    private static boolean handleResponse() throws IOException, InterruptedException {
        if (selector.select() == 0) {
            return true;
        }

        for (SelectionKey key : selector.selectedKeys()) {
            if (!key.isReadable()) {
                continue;
            }

            SocketChannel clientChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int bytesRead = clientChannel.read(buffer);
            if (bytesRead == -1) {
                logger.error("Server Disconnected");
                return false;
            }

            buffer.flip();
            String responseJSON = new String(buffer.array(), buffer.position(), buffer.limit());
            buffer.clear();

            Response<?> response = gson.fromJson(responseJSON, Response.class);

            // TODO: Clean up requests in the `responseQueues`
            if (response.getRequestId() == null) {
                logger.error("Invalid Response: " + response.getBody().toString());
                continue;
            }

            responseQueues.get(response.getRequestId()).put(responseJSON);
        }
        selector.selectedKeys().clear();

        return true;
    }

    public static <ResponseBody> CompletableFuture<Response<ResponseBody>> get(String endpoint) {
        return CompletableFuture.supplyAsync(() -> {
            if (!isConnectionActive()) {
                throw new CompletionException(new RuntimeException("Connection Failed"));
            }

            HashMap<EHeader, String> headers = new HashMap<>();
            return request(new Request<>(EMethod.GET, endpoint, null, headers));
        }, executor);
    }

    public static <RequestBody, ResponseBody> CompletableFuture<Response<ResponseBody>> post(String endpoint,
            RequestBody body) {
        return CompletableFuture.supplyAsync(() -> {
            if (!isConnectionActive()) {
                throw new CompletionException(new RuntimeException("Connection Failed"));
            }

            HashMap<EHeader, String> headers = new HashMap<>();
            return request(new Request<>(EMethod.POST, endpoint, body, headers));
        }, executor);
    }

    private static <RequestBody, ResponseBody> Response<ResponseBody> request(
            Request<RequestBody> request) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            String requestJSON = gson.toJson(request);
            buffer.put(requestJSON.getBytes());
            buffer.flip();

            logger.info(
                    String.format("%s -> %s", request.getMethod(),
                            request.getEndpoint()));

            int bytesWritten = serverChannel.write(buffer);

            if (bytesWritten == -1) {
                throw new CompletionException(new RuntimeException("Server Disconnected"));
            }

            responseQueues.put(request.getRequestId(), new LinkedBlockingQueue<>());
            String responseJSON = responseQueues.get(request.getRequestId()).poll(5, TimeUnit.SECONDS);
            responseQueues.remove(request.getRequestId());

            if (responseJSON == null) {
                throw new CompletionException(new RuntimeException("Request Timed Out"));
            }

            @SuppressWarnings("unchecked")
            Response<ResponseBody> response = gson.fromJson(responseJSON, Response.class);
            Class<?> clazz = Class.forName(response.getBodyType());
            Type type = TypeToken.getParameterized(Response.class, clazz).getType();
            response = gson.fromJson(responseJSON, type);

            logger.info(
                    String.format("%s <- %s", response.getStatus().toString(),
                            response.getEndpoint()));

            if (response.getStatus() != Response.EStatus.OK) {
                throw new CompletionException(new RuntimeException(response.getBody().toString()));
            }

            return response;
        } catch (IOException e) {
            handleClose();
            throw new CompletionException(e);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }
}
