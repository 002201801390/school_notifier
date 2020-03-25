package com.github.app.handlers;

import com.sun.net.httpserver.HttpHandler;

public interface ConfigurationHandler {

    String path();

    HttpHandler handler();
}