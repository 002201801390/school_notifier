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
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password,
                                     @FormParam("module")   String module) {

        final String userId = authenticate(username, password, module);
        if (InputUtils.validString(userId)) {
            logger.debug("User authenticated");

            String token = TokenUtils.getExistingTokenOrCreateOne(userId, module);
            if (InputUtils.validString(token)) {
                return Response.ok(token).build();
            }
            logger.debug("Error to get/generate token");
            return Response.serverError().build();
        }

        logger.debug("Access denied!");
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Secured
    @Path("/check")
    public Response checkAuthentication() {
        return Response.ok().build();
    }

    private static @Nullable String authenticate(String username, String password, String module) {
        Objects.requireNonNull(username, "Username can't be null");
        Objects.requireNonNull(password, "Password can't be null");
        Objects.requireNonNull(module, "Module can't be null");

        Connection connection = DBConnection.gi().connection();

        final String sql = "SELECT id, password, role from users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("No user founded");
            }

            final String userId = resultSet.getString("id");
            final String pass = resultSet.getString("password");
            final String role = resultSet.getString("role");

            final String encryptPass = AuthenticationUtils.encryptUserPassword(userId, password);

            if (Objects.equals(pass, encryptPass) && userCanAccessModule(module, role)) {
                return userId;
            }
        } catch (SQLException | RuntimeException e) {
            logger.error("Error to authenticate user");
        }

        return null;
    }

    private static boolean userCanAccessModule(@NotNull String module, @NotNull String role) {
        switch (role) {
            case "responsible":
            case "student":
                return module.equalsIgnoreCase("mobile");

            // TODO: REMOVE
            case "admin":
                return true;
        }
        return false;
    }
}
