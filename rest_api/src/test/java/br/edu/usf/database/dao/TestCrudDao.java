package br.edu.usf.database.dao;

import br.edu.usf.database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public abstract class TestCrudDao {

    @Test
    @Order(1)
    public abstract void testCreate();

    @Test
    @Order(2)
    public abstract void testRead();

    @Test
    @Order(3)
    public abstract void testUpdate();

    @Test
    @Order(4)
    public abstract void testDelete();

//    @AfterAll
//    public static void closeDatabaseConnection() {
//        DBConnection.gi().closeConnection();
//    }
}
