package br.edu.usf.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);

    private static final DBConnection instance = new DBConnection();

    private static final String host = "127.0.0.1";
    private static final String db   = "school_notifier";
    private static final String user = "postgres";
    private static final String pass = "pass";

    private Connection conn;

    public static DBConnection gi() {
        return instance;
    }

    private DBConnection() {
        super();
    }

    private void tryToConnect() {
        String url = "jdbc:postgresql://" + host + "/" + db;

        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            logger.error("Error to connect with postgres database", e);
        }
    }

    public Connection connection() {
        if (conn == null) {
            tryToConnect();
        }

        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("Error to close database connection", e);
        }
    }
}