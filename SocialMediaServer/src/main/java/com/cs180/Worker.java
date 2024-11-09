package com.cs180;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import javax.lang.model.type.NullType;

import com.cs180.api.Request;
import com.cs180.api.Response;
import com.cs180.api.Request.EMethod;
import com.cs180.resolvers.ResolverTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Worker implements Runnable {
    private Selector selector;

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public Worker(Selector selector) {
        this.selector = selector;
    }

    private void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int bytesRead = clientChannel.read(buffer);
            if (bytesRead == -1) {
                System.out.println("Client disconnected: " + clientChannel);
                clientChannel.close();
            } else {
                buffer.flip();
                String json = new String(buffer.array()).trim();
                try {
                    @SuppressWarnings("unchecked")
                    Request<NullType> request = gson.fromJson(json, Request.class);

                    // temprorary logger
                    System.out.printf("[INFO] %s -> %s -> %s\n", clientChannel.getRemoteAddress(), request.getMethod(),
                            request.getEndpoint());

                    String response = ResolverTools.resolve(request, json);
                    buffer.clear().put(response.getBytes());
                    buffer.flip();

                    clientChannel.write(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Unknown Exception");
                    Response<String> response = new Response<>(EMethod.UNKNOWN, "unknown", "Failed to parse JSON",
                            Response.EStatus.BAD_REQUEST);
                    String responseJSON = gson.toJson(response);
                    buffer.clear().put(responseJSON.getBytes());
                    buffer.flip();

                    clientChannel.write(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                clientChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.selector.select();
                Iterator<SelectionKey> keyIterator = this.selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    // Technically unnecessary since selector only registered
                    // to accept READ events, but it's good practice to check
                    if (!key.isReadable())
                        continue;

                    handleRead(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
