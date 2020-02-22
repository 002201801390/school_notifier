package com.github.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.logger.Log;

public class DBConnection {
    private static final String host = "127.0.0.1";
    private static final String db   = "school_notifier";
    private static final String user = "postgres";
    private static final String pass = "pass";

    private static Connection conn;

    private DBConnection() {
        throw new AssertionError("No " + DBConnection.class + " instances for you!");
    }

    private static void tryToConnect() {
        String url = "jdbc:postgresql://" + host + "/" + db;

        try {
            conn = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            Log.ERRO("Error to connect with postgres database", e);
        }
    }

    public static Connection connection() {
        if (conn == null) {
            tryToConnect();
        }

        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            Log.ERRO("Error to close database connection", e);
        }
    }
}