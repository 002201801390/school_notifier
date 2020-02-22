package com.github.app.handlers.path;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;

import com.github.app.handlers.ConfigurationHandler;
import com.github.logger.Log;
import com.github.utils.HttpMethod;
import com.github.utils.Utils;

public abstract class ApplicationContext implements ConfigurationHandler {

    public String GET(final Map<String, List<String>> params) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

    public String POST(final Map<String, List<String>> params) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

    public String path() {
        return getClass().getPackageName()
                .replace(ApplicationContext.class.getPackageName(), "")
                .replace(".", "/") + "/" + getClass()
                .getSimpleName().toLowerCase();
    }

    public HttpHandler handler() {
        return exchange -> {
            try {
                Map<String, List<String>> params = Utils.splitQuery(exchange.getRequestURI().getRawQuery());
                String ret;

                switch (exchange.getRequestMethod()) {
                    case HttpMethod.GET:
                        ret = GET(params);
                        break;

                    case HttpMethod.POST:
                        ret = POST(params);
                        break;

                    default:
                        throw new RuntimeException("Invalid HTTP method");
                }

                byte[] retBytes = ret.getBytes();
                exchange.sendResponseHeaders(200, retBytes.length);

                OutputStream output = exchange.getResponseBody();
                output.write(retBytes);
                output.flush();

            } catch (Throwable e) {
                Log.ERRO("Error while processing HTTP request", e);
                exchange.sendResponseHeaders(405, -1);

            } finally {
                exchange.close();
            }
        };
    }
}