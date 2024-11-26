package com.lewall;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Worker.WorkerRequestRef;
import com.lewall.db.Database;
import com.lewall.resolvers.AuthResolver;
import com.lewall.resolvers.CommentResolver;
import com.lewall.resolvers.PostResolver;
import com.lewall.resolvers.ResolverTools;
import com.lewall.resolvers.RootResolver;
import com.lewall.resolvers.UserResolver;

/**
 * 
 * This is the entry point of the Social Media Server program.
 * Sets up a multi-threaded server listening on port {@value #PORT}.
 * 
 * Architecture inspired by {@link https://www.youtube.com/watch?v=h76n2R4HRts}
 * 
 * @author Mahit Mehta
 * @version 2024-11-06
 * 
 */
public class AppServer {
    private static final Logger logger = LogManager.getLogger(AppServer.class);

    private static final int PORT = 8080;
    private static final int PHYSICAL_THREAD_COUNT = 4;

    private ExecutorService workers;

    private LinkedTransferQueue<WorkerRequestRef> receiver = new LinkedTransferQueue<>();

    public static final String ID = "LeWallServer";

    private void start() throws IOException {
        Database.init();
        ResolverTools.init(
                new RootResolver(),
                new AuthResolver(),
                new UserResolver(),
                new CommentResolver(),
                new PostResolver());

        Selector connSelector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);
        serverChannel.register(connSelector, SelectionKey.OP_ACCEPT);

        workers = Executors.newFixedThreadPool(PHYSICAL_THREAD_COUNT);

        for (int t = 0; t < PHYSICAL_THREAD_COUNT; t++) {
            workers.submit(new Worker(receiver));
        }

        logger.info("Server started on port " + PORT);

        while (true) {
            if (connSelector.select() == 0) {
                continue;
            }

            for (SelectionKey key : connSelector.selectedKeys()) {
                if (key.isAcceptable() && key.channel() instanceof ServerSocketChannel channel) {
                    SocketChannel clientChannel = channel.accept();

                    clientChannel.configureBlocking(false);
                    clientChannel.register(connSelector, SelectionKey.OP_READ);
                    logger.info("Accepted new connection from " + clientChannel);
                } else if (key.isReadable() && key.channel() instanceof SocketChannel) {
                    handleRead(key);
                }
            }
            connSelector.selectedKeys().clear();
        }
    }

    private void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(Worker.MTU);
        try {
            int bytesRead = clientChannel.read(buffer);
            if (bytesRead == -1) {
                logger.info("Client disconnected: " + clientChannel);
                clientChannel.close();
                key.cancel();

                return; // Client disconnected, No further processing
            } else {
                buffer.flip();
                try {
                    receiver.transfer(new Worker.WorkerRequestRef(clientChannel, buffer));
                    return; // Transfer successful, No further processing
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        } catch (IOException e) {
            logger.error(e);
            try {
                clientChannel.close();
                key.cancel();

                return; // Client disconnected, No further processing
            } catch (IOException ex) {
                logger.error("Error Closing Client Channel", ex);
            }
        }

        // Consider sending a `Server Error` response to the client
    }

    /**
     * Entry point of the program
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        AppServer server = new AppServer();
        server.start();
    }
}
