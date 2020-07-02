package br.edu.usf.utils;

import br.edu.usf.model.LoggablePerson;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class AuthenticationUtils {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationUtils.class);
    private static final String algorithm = "SHA-512";

    private AuthenticationUtils() {
        throw new AssertionError("No " + AuthenticationUtils.class + " instances for you!");
    }

    public static @NotNull String encryptUserPassword(@NotNull LoggablePerson person) throws RuntimeException {
        Objects.requireNonNull(person, "Person cannot be null");

        final String personId = person.getId();
        final String password = person.getPassword();

        return encryptUserPassword(personId, password);
    }

    public static void main(String[] args) {
        System.out.println(encryptUserPassword("61143bcd-2e5f-4ea0-8fb0-fa10aaf044c9", "lalalu"));
    }

    public static @NotNull String encryptUserPassword(@NotNull String personId, @NotNull String password) throws RuntimeException {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(personId.getBytes(StandardCharsets.UTF_8));

            byte[] digestBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            final StringBuilder builder = new StringBuilder();
            for (byte aByte : digestBytes) {
                builder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return builder.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error("The algorithm '" + algorithm + "' isn't available in the current platform");

            throw new RuntimeException(e);
        }
    }
}
