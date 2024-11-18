package com.lewall;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import javax.lang.model.type.NullType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Request;
import com.lewall.api.Response;
import com.lewall.api.Request.EMethod;
import com.lewall.resolvers.ResolverTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Worker class to handle client requests
 * 
 * @author Mahit Mehta
 * @version November 17, 2024
 */
public class Worker implements Runnable {
    private static final Logger logger = LogManager.getLogger(Worker.class);

    private Selector selector;
    private final int workerId;

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    private static final AtomicInteger workerCount = new AtomicInteger(0);

    public Worker(Selector selector) {
        this.selector = selector;

        workerId = workerCount.getAndIncrement();
    }

    /**
     * Handles reading from the client
     * 
     * @param key
     *            SelectionKey for the client
     */
    private void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int bytesRead = clientChannel.read(buffer);
            if (bytesRead == -1) {
                logger.info("Client disconnected: " + clientChannel);
                clientChannel.close();
            } else {
                buffer.flip();
                String json = new String(buffer.array()).trim();
                try {
                    @SuppressWarnings("unchecked")
                    Request<NullType> request = gson.fromJson(json, Request.class);

                    logger.info(
                            String.format("[%s] %s -> %s -> %s", workerId, clientChannel.getRemoteAddress(),
                                    request.getMethod(),
                                    request.getEndpoint()));

                    String response = ResolverTools.resolve(request, json);

                    @SuppressWarnings("unchecked")
                    Response<NullType> responseObj = gson.fromJson(response, Response.class);
                    logger.info(
                            String.format("[%s] %s <- %s <- %s", workerId, clientChannel.getRemoteAddress(),
                                    responseObj.getStatus().toString(),
                                    request.getEndpoint()));

                    buffer.clear().put(response.getBytes());
                    buffer.flip();

                    clientChannel.write(buffer);
                } catch (Exception e) {
                    logger.error("Unknown Exception", e);

                    Response<String> response = new Response<>(EMethod.UNKNOWN, "Unknown Endpoint", "Unknown Exception",
                            Response.EStatus.SERVER_ERROR, null);

                    logger.info(
                            String.format("[%s] %s <- %s <- %s", workerId, clientChannel.getRemoteAddress(),
                                    response.getStatus().toString(),
                                    response.getEndpoint()));

                    String responseJSON = gson.toJson(response);
                    buffer.clear().put(responseJSON.getBytes());
                    buffer.flip();

                    clientChannel.write(buffer);
                }
            }
        } catch (IOException e) {
            logger.error(e);
            try {
                clientChannel.close();
            } catch (IOException ex) {
                logger.error("Error Closing Client Channel", ex);
            }
        }
    }

    /**
     * Main worker loop
     * 
     * @see java.lang.Runnable#run()
     */
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
            logger.error(e);
        }
    }
}
