package br.edu.usf.auth;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.Employee;
import br.edu.usf.model.LoggablePerson;
import br.edu.usf.utils.AuthenticationUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class PasswordEncryptionTest {

    private static LoggablePerson person;

    @BeforeAll
    public static void prepareLoggableUser() {
        person = new Employee();
        person.setId(UUID.randomUUID().toString());
        person.setPassword(UUID.randomUUID().toString());
    }

    @Test
    public void encryptionTest() {
        final String originalPass = person.getPassword();
        final String encryptedPass = AuthenticationUtils.encryptUserPassword(person);

        Assertions.assertNotEquals(originalPass, encryptedPass);
    }

    @Test
    public void encryptionComparativeTest() {
        final String firstEncryption = AuthenticationUtils.encryptUserPassword(person);
        final String secondEncryption = AuthenticationUtils.encryptUserPassword(person);

        Assertions.assertEquals(firstEncryption, secondEncryption);
    }

//    @AfterAll
//    public static void closeDatabaseConnection() {
//        DBConnection.gi().closeConnection();
//    }
}
