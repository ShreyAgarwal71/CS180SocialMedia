package com.cs180.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cs180.api.Request.EHeader;
import com.cs180.api.Request.EMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Connection {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private static SocketChannel serverChannel;

    private static ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private static final Object lock = new Object();

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public static boolean connect() throws IOException {
        if (serverChannel != null && serverChannel.isConnected()) {
            return true;
        }

        try {
            serverChannel = SocketChannel.open();
            serverChannel.connect(new InetSocketAddress(HOST, PORT));
            serverChannel.configureBlocking(true);
            System.out.println("Connected to Server");
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static <ResponseBody> CompletableFuture<Response<ResponseBody>> get(String endpoint) {
        if (serverChannel == null || !serverChannel.isConnected()) {
            throw new CompletionException(new RuntimeException("Connection not established"));
        }

        HashMap<EHeader, String> headers = new HashMap<>();
        return request(new Request<>(EMethod.GET, endpoint, null, headers));
    }

    public static <RequestBody, ResponseBody> CompletableFuture<Response<ResponseBody>> post(String endpoint,
            RequestBody body) {
        if (serverChannel == null || !serverChannel.isConnected()) {
            throw new CompletionException(new RuntimeException("Connection not established"));
        }

        HashMap<EHeader, String> headers = new HashMap<>();
        return request(new Request<>(EMethod.POST, endpoint, body, headers));
    }

    private static <RequestBody, ResponseBody> CompletableFuture<Response<ResponseBody>> request(
            Request<RequestBody> request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                String requestJSON = gson.toJson(request);
                buffer.put(requestJSON.getBytes());
                buffer.flip();

                synchronized (lock) {
                    serverChannel.write(buffer);
                    buffer.clear();
                    serverChannel.read(buffer);
                }

                buffer.flip();
                String responseJSON = new String(buffer.array(), buffer.position(), buffer.limit());
                @SuppressWarnings("unchecked")
                Response<ResponseBody> response = gson.fromJson(responseJSON, Response.class);
                Class<?> clazz = Class.forName(response.getBodyType());
                Type type = TypeToken.getParameterized(Response.class, clazz).getType();
                response = gson.fromJson(responseJSON, type);

                if (response.getStatus() != Response.EStatus.OK) {
                    throw new CompletionException(new RuntimeException(response.getBody().toString()));
                }

                return response;
            } catch (IOException e) {
                throw new CompletionException(e);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }
}
