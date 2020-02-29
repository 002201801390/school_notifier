package com.github.app.context;

import java.util.Objects;

import com.github.app.handlers.ConfigurationHandler;
import com.github.logger.Log;
import com.sun.net.httpserver.HttpServer;

public class ContextManager {

    private ContextManager() {
        throw new AssertionError("No " + ContextManager.class + " instances for you!");
    }

    public static void setupContext(HttpServer server, ConfigurationHandler handler) {
        Objects.requireNonNull(server, "HttpServer can't be null");
        Objects.requireNonNull(handler, "Handler can't be null");

        String path = handler.path();
        server.createContext(path, handler.handler());
        Log.ERRO("Creating context in " + path);
    }
}