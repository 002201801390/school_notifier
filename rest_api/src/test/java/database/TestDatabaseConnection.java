package database;

import com.github.database.DBConnection;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class TestDatabaseConnection {

    @Test
    public void testConnection() {
        Assert.assertNotNull("Database connection null", DBConnection.connection());
    }

    @Test
    public void testTeste() {
        Assert.assertNotNull("Database connection null", DBConnection.connection());
    }


    @AfterClass
    public static void closeDatabaseConnection() {
        DBConnection.closeConnection();
    }
}
