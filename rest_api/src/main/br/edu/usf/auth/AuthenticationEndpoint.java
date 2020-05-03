package br.edu.usf.auth;

import br.edu.usf.database.DBConnection;
import br.edu.usf.utils.AuthenticationUtils;
import br.edu.usf.utils.InputUtils;
import br.edu.usf.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Path("/auth")
public class AuthenticationEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEndpoint.class);

    @POST
    public Response authenticateUser(@FormParam("login") String login, @FormParam("password") String password) {

        final String userId = authenticate(login, password);
        if (InputUtils.validString(userId)) {
            logger.debug("User authenticated");

            String token = TokenUtils.generateToken(userId);

            return Response.ok(token).build();
        }

        logger.debug("Invalid credentials");
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private static @Nullable String authenticate(@NotNull String login, @NotNull String password) {
        Objects.requireNonNull(login, "Login ID can't be null");
        Objects.requireNonNull(password, "Password ID can't be null");

        Connection connection = DBConnection.gi().connection();

        final String sql = "SELECT id, password from users WHERE login = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);

            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("No user founded");
            }

            final String userId = resultSet.getString("id");
            final String pass = resultSet.getString("password");

            final String encryptPass = AuthenticationUtils.encryptUserPassword(userId, password);

            if (Objects.equals(pass, encryptPass)) {
                return userId;
            }
        } catch (SQLException | RuntimeException e) {
            logger.error("Error to authenticate user");
        }

        return null;
    }
}
