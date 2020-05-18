package br.edu.usf.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDatabaseConnection {

    @Test
    public void testConnection() {
        Assertions.assertNotNull(DBConnection.gi().connection(), "Database connection null");
    }

//    @AfterAll
//    public static void closeDatabaseConnection() {
//        DBConnection.gi().closeConnection();
//    }
}
