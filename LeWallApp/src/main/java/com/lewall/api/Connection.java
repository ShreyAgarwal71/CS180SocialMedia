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

/**
 * Static class to manage client-server communication in the application
 * 
 * @author Mahit Mehta
 * @version 16 November 2024
 */
public class Connection {
    private static final Logger logger = LogManager.getLogger(Connection.class);

    private static final String REMOTE_HOST = "lewall.mahitm.com";
    private static final String LOCAL_HOST = "localhost";
    private static final int PORT = 8559;

    private static SocketChannel serverChannel;

    private static ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    private static Selector selector;
    private static HashMap<UUID, LinkedBlockingQueue<String>> responseQueues = new HashMap<>();

    /**
     * Connects to the server
     * 
     * @return true if connection is successful, false otherwise
     */
    public static synchronized boolean connect() {
        if (serverChannel != null && serverChannel.isConnected()) {
            return true;
        }

        try {
            selector = Selector.open();
            serverChannel = SocketChannel.open();

            String host = System.getenv("REMOTE") != null ? REMOTE_HOST : LOCAL_HOST;

            serverChannel.connect(new InetSocketAddress(host, PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_READ);

            logger.info("Connected to Host: " + host);

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

    /**
     * Closes the connection to the server
     */
    private synchronized static void handleClose() {
        try {
            serverChannel.close();
            selector.close();

            logger.info("Closed Connection");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Checks if the connection is active
     * Also attempts to reconnect if connection is inactive
     * 
     * @return true if connection is active, false otherwise
     */
    private static boolean isConnectionActive() {
        if (serverChannel == null || !serverChannel.isConnected()) {
            logger.debug("Attempting Reconnection");
            return connect();
        }

        return serverChannel.isConnected();
    }

    /**
     * Handles the server response
     * 
     * @return true if response was handled successfully, false otherwise
     * @throws IOException
     * @throws InterruptedException
     */
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

            if (response.getRequestId() == null) {
                logger.error("Invalid Response: " + response.getBody().toString());
                continue;
            }

            responseQueues.get(response.getRequestId()).put(responseJSON);
        }
        selector.selectedKeys().clear();

        return true;
    }

    /**
     * Gets the base headers for the request
     * and adds the access token if available
     * 
     * @return base headers
     */
    private static HashMap<EHeader, String> getBaseHeaders() {
        HashMap<EHeader, String> headers = new HashMap<>();
        String token = LocalStorage.get("token");
        if (token != null)
            headers.put(EHeader.ACCESS_TOKEN, token);
        return headers;
    }

    /**
     * Sends a GET request to the server
     * 
     * @param endpoint
     * @param cache
     * @return response from the server
     */
    public static <ResponseBody> CompletableFuture<Response<ResponseBody>> get(String endpoint, boolean cache) {
        return CompletableFuture.supplyAsync(() -> {
            if (!isConnectionActive()) {
                throw new CompletionException(new RuntimeException("Connection Failed"));
            }

            HashMap<EHeader, String> headers = getBaseHeaders();
            return request(new Request<>(EMethod.GET, endpoint, null, headers), cache);
        }, executor);
    }

    /**
     * Sends a POST request to the server
     * 
     * @param endpoint
     * @param body
     * @return response from the server
     */
    public static <RequestBody, ResponseBody> CompletableFuture<Response<ResponseBody>> post(String endpoint,
            RequestBody body) {
        return CompletableFuture.supplyAsync(() -> {
            if (!isConnectionActive()) {
                throw new CompletionException(new RuntimeException("Connection Failed"));
            }

            HashMap<EHeader, String> headers = getBaseHeaders();
            return request(new Request<>(EMethod.POST, endpoint, body, headers), false);
        }, executor);
    }

    /**
     * Sends a request to the server
     * 
     * @param endpoint
     * @param body
     * @return response from the server
     */
    private static <RequestBody, ResponseBody> Response<ResponseBody> request(
            Request<RequestBody> request, boolean cache) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            String requestJSON = gson.toJson(request);
            byte[] requestBytes = requestJSON.getBytes();
            int requestLength = requestBytes.length;

            buffer.putInt(requestLength);
            buffer.put(requestBytes);
            buffer.flip();

            logger.info(
                    String.format("%s -> %s", request.getMethod(),
                            request.getEndpoint()));

            int bytesWritten = serverChannel.write(buffer);

            if (bytesWritten == -1) {
                throw new CompletionException(new RuntimeException("Server Disconnected"));
            }

            responseQueues.put(request.getRequestId(), new LinkedBlockingQueue<>());
            String responseJSON = responseQueues.get(request.getRequestId()).poll(10, TimeUnit.SECONDS);
            responseQueues.remove(request.getRequestId());

            if (responseJSON == null) {
                handleClose();
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

            if (cache) {
                String responseBodyJSON = gson.toJson(response.getBody());
                LocalStorage.set(request.getEndpoint(), responseBodyJSON);
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
