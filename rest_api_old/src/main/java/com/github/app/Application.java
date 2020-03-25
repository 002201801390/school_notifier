package com.github.app;

import java.net.InetSocketAddress;

import com.github.app.context.ContextManager;
import com.github.app.handlers.path.SayHello;
import com.github.logger.Log;
import com.sun.net.httpserver.HttpServer;

class Application {

    private static final int PORT = 8000;

    public static void main(final String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> Log.close(), "ShutdownHook"));
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
            if (!Log.isClosed()) {
                Log.ERRO("UncaughtException in thread " + thread, e);
            } else {
                e.printStackTrace();
            }
        });

        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            ContextManager.setupContext(server, new SayHello());

            server.start();

        } catch (final Throwable e) {
            System.out.println(">>>>>>>>>>[SYSTEM-ERROR]<<<<<<<<<<");
            e.printStackTrace();
            System.exit(0);
        }
    }
}