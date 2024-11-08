package com.cs180;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cs180.db.Database;
import com.cs180.resolvers.ResolverTools;

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
    private static final int PORT = 8080;
    private static final int PHYSICAL_THREAD_COUNT = 4;

    private Selector[] selectors;
    private ExecutorService workers;

    public static final String ID = "SocialMediaServer";

    private void start() throws IOException {
        Database.init();

        Selector connSelector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);
        serverChannel.register(connSelector, SelectionKey.OP_ACCEPT);

        selectors = new Selector[PHYSICAL_THREAD_COUNT];
        workers = Executors.newFixedThreadPool(PHYSICAL_THREAD_COUNT);

        for (int t = 0; t < PHYSICAL_THREAD_COUNT; t++) {
            selectors[t] = Selector.open();
            final int selectorIndex = t;

            workers.submit(new Worker(selectors[selectorIndex]));
        }

        System.out.println("Server started on port " + PORT);

        int nextSelector = 0;
        while (true) {
            if (connSelector.select() == 0) {
                continue;
            }

            for (SelectionKey key : connSelector.selectedKeys()) {
                // Technically unnecessary since selector only registered
                // to accept ACCEPT events, but it's good practice to check
                if (!key.isAcceptable()) {
                    continue;
                }

                if (key.channel() instanceof ServerSocketChannel channel) {
                    SocketChannel clientChannel = channel.accept();

                    clientChannel.configureBlocking(false);
                    selectors[nextSelector].wakeup();
                    clientChannel.register(selectors[nextSelector], SelectionKey.OP_READ);
                    System.out.println("Accepted new connection from " + clientChannel);

                    // Perform round-robin selection of selectors
                    if (nextSelector == PHYSICAL_THREAD_COUNT - 1)
                        nextSelector = 0;
                    else
                        nextSelector++;
                }
            }
            connSelector.selectedKeys().clear();
        }
    }

    public static void main(String[] args) throws IOException {
        AppServer server = new AppServer();
        server.start();
    }
}
