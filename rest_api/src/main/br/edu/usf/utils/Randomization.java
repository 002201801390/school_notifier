package br.edu.usf.utils;

import br.edu.usf.model.LoggablePerson;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Randomization {
    private static final Logger logger = LoggerFactory.getLogger(Randomization.class);

    private static JsonObject currentPerson;
    private static URL randomURL;

    static {
        try {
            randomURL = new URL("https://randomuser.me/api/");

        } catch (MalformedURLException e) {
            logger.error("Error to create Randomization URL");
        }
    }

    private Randomization() {
        throw new AssertionError("No " + Randomization.class + " instances for you!");
    }

    public static @Nullable String id() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            return UUID.randomUUID().toString();
        }
        return null;
    }

    public static @Nullable String name() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            final JsonObject name = randomPerson.get("name").getAsJsonObject();
            return name.get("first").getAsString() + " " + name.get("last").getAsString();
        }
        return null;
    }

    public static @Nullable LocalDate dtBirth() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            final JsonObject dob = randomPerson.get("dob").getAsJsonObject();
            return DaoUtils.stringToLocalDate(dob.get("date").getAsString());
        }
        return null;
    }

    public static @Nullable String username() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            final JsonObject username = randomPerson.get("login").getAsJsonObject();
            return username.get("username").getAsString();
        }
        return null;
    }

    public static @Nullable String password() {
        final JsonObject randomPerson = randomPerson();
        final String id = id();

        if (randomPerson != null && id != null) {
            final JsonObject username = randomPerson.get("login").getAsJsonObject();
            return AuthenticationUtils.encryptUserPassword(id, username.get("password").getAsString());
        }
        return null;
    }

    public static @Nullable String email() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            return randomPerson.get("email").getAsString();
        }
        return null;
    }

    public static @Nullable String phone() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            return randomPerson.get("phone").getAsString();
        }
        return null;
    }

    private static @Nullable JsonObject randomPerson() {
        if (currentPerson != null) {
            return currentPerson;
        }

        currentPerson = createRandomPerson();
        return currentPerson;
    }

    @Nullable
    private static JsonObject createRandomPerson() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(randomURL.toString());
            request.addHeader("content-type", "application/json");

            final CloseableHttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            return new Gson().fromJson(json, JsonObject.class).get("results").getAsJsonArray().get(0).getAsJsonObject();

        } catch (IOException ex) {
            logger.error("Error to create a random person");
        }
        return null;
    }

    public static void flushPerson() {
        currentPerson = null;
    }

    public static void fillWithRandomPerson(LoggablePerson person) {
        Objects.requireNonNull(person, "Person cannot be null");

        Randomization.flushPerson();

        person.setName(Randomization.name());
//        person.setDtBirth(Randomization.dtBirth());
        person.setEmail(Randomization.email());
        person.setPhone(Randomization.phone());
        person.setUsername(Randomization.username());
        person.setPassword(Randomization.password());
    }
}
