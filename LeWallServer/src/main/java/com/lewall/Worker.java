package com.lewall;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedTransferQueue;
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
    public static final int MTU = 1024 * 16; // 16KB

    private static final Logger logger = LogManager.getLogger(Worker.class);

    private LinkedTransferQueue<WorkerRequestRef> receiver;
    private final int workerId;

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    private static final AtomicInteger workerCount = new AtomicInteger(0);

    public static class WorkerRequestRef {
        private final SocketChannel clientChannel;
        private final ByteBuffer buffer;

        public WorkerRequestRef(SocketChannel clientChannel, ByteBuffer buffer) {
            this.clientChannel = clientChannel;
            this.buffer = buffer;
        }

        public SocketChannel getClientChannel() {
            return clientChannel;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }
    }

    public Worker(LinkedTransferQueue<WorkerRequestRef> receiver) {
        this.receiver = receiver;

        workerId = workerCount.getAndIncrement();
    }

    /**
     * Handle a single request
     * 
     * @param ref
     */
    private void handleRequest(WorkerRequestRef ref) throws IOException {
        SocketChannel clientChannel = ref.getClientChannel();
        ByteBuffer reqBuffer = ref.getBuffer();
        ByteBuffer resBuffer = ByteBuffer.allocate(MTU);

        reqBuffer.flip();
        String json = new String(reqBuffer.array()).trim();

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

            byte[] responseBytes = response.getBytes();
            int responseLength = responseBytes.length;
            resBuffer.putInt(responseLength);
            resBuffer.put(responseBytes);
            resBuffer.flip();

            clientChannel.write(resBuffer);
        } catch (Exception e) {
            logger.error("Unknown Exception", e);

            Response<String> response = new Response<>(EMethod.UNKNOWN, "Unknown Endpoint", "Unknown Exception",
                    Response.EStatus.SERVER_ERROR, null);

            logger.info(
                    String.format("[%s] %s <- %s <- %s", workerId, clientChannel.getRemoteAddress(),
                            response.getStatus().toString(),
                            response.getEndpoint()));

            String responseJSON = gson.toJson(response);
            resBuffer.put(responseJSON.getBytes());
            resBuffer.flip();

            clientChannel.write(resBuffer);
        }
    }

    /**
     * Main worker loop
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (true) {
            try {
                WorkerRequestRef ref = this.receiver.take();
                handleRequest(ref);
            } catch (InterruptedException e) {
                logger.error("Error Taking from Receiver: ", e);
            } catch (IOException e) {
                logger.error("Error Handling Request: ", e);
            }
        }
    }
}
