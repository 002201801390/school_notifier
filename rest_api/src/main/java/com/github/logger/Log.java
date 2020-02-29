package com.github.logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class Log {

    private static final Logger DEFAULT_LOGGER = Logger.getAnonymousLogger();

    private static Level LOG_LEVEL = Level.ALL;
    private static FileHandler handler;

    private static boolean closed = false;

    static {
        // log format
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");

        // prevent log in console
        Logger rootLogger = Logger.getGlobal();

        Collection<Handler> handlers = Arrays.asList(rootLogger.getHandlers());
        Collection<Handler> consoleHandlers = handlers.stream().filter(h -> h instanceof ConsoleHandler)
                .collect(Collectors.toList());

        for (Handler h : consoleHandlers) {
            rootLogger.removeHandler(h);
        }

        // configure file path and log formatter
        try {
            handler = new FileHandler("school-notifier.log", true);
            handler.setFormatter(new SimpleFormatter());

        } catch (IOException e) {
            e.printStackTrace();
        }

        startingLog();
    }

    private Log() {
        throw new AssertionError("No " + Log.class + " instances for you!");
    }

    public static void ERRO(String message) {
        ERRO(message, null);
    }

    public static void ERRO(String message, Throwable throwable) {
        ERRO(DEFAULT_LOGGER, message, throwable);
    }

    public static void ERRO(Class<?> clazz, String message, Throwable throwable) {
        Objects.requireNonNull(clazz, "Cass can't be null");

        Logger logger = LogManager.getLogManager().getLogger(clazz.getName());
        logger = setupLogger(logger);
        logger.log(SEVERE, message, throwable);
    }

    public static void ERRO(Logger logger, String message, Throwable throwable) {
        LOG(SEVERE, logger, message, throwable);
    }

    public static void INFO(String message) {
        INFO(message, null);
    }

    public static void INFO(String message, Throwable throwable) {
        INFO(DEFAULT_LOGGER, message, throwable);
    }

    public static void INFO(Class<?> clazz, String message, Throwable throwable) {
        Objects.requireNonNull(clazz, "Cass can't be null");

        Logger logger = LogManager.getLogManager().getLogger(clazz.getName());
        INFO(logger, message, throwable);
    }

    public static void INFO(Logger logger, String message, Throwable throwable) {
        LOG(INFO, logger, message, throwable);
    }

    public static void WARN(String message) {
        WARN(message, null);
    }

    public static void WARN(String message, Throwable throwable) {
        WARN(DEFAULT_LOGGER, message, throwable);
    }

    public static void WARN(Class<?> clazz, String message, Throwable throwable) {
        Objects.requireNonNull(clazz, "Cass can't be null");

        Logger logger = LogManager.getLogManager().getLogger(clazz.getName());
        WARN(logger, message, throwable);
    }

    public static void WARN(Logger logger, String message, Throwable throwable) {
        LOG(WARNING, logger, message, throwable);
    }

    private static void LOG(Level level, Logger logger, String message, Throwable throwable) {
        logger = setupLogger(logger);
        logger.log(level, message, throwable);
    }

    public static void setLevel(Level level) {
        LOG_LEVEL = level;
    }

    private static Logger setupLogger(Logger logger) {
        if (closed) {
            throw new IllegalStateException("Log already closed");
        }

        if (Objects.isNull(logger)) {
            logger = DEFAULT_LOGGER;
        }

        Collection<Handler> loggerHandlers = Arrays.asList(logger.getHandlers());

        logger.setLevel(LOG_LEVEL);

        if (!loggerHandlers.contains(handler)) {
            logger.addHandler(handler);
        }

        return logger;
    }

    private static void startingLog() {
        Level bkpLevel = LOG_LEVEL;
        LOG_LEVEL = Level.ALL;

        INFO("=======================");
        INFO("STARTING APPLICATION...");
        INFO("=======================");
        
        LOG_LEVEL = bkpLevel;
    }

    private static void endingLog() {
        Level bkpLevel = LOG_LEVEL;
        LOG_LEVEL = Level.ALL;

        INFO("=======================");
        INFO("ENDING APPLICATION...");
        INFO("=======================");

        LOG_LEVEL = bkpLevel;
    }

    public static void close() {
        endingLog();
        handler.close();

        closed = true;
    }

    public static boolean isClosed() {
        return closed;
    }
}