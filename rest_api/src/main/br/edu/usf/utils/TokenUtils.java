package br.edu.usf.utils;

import br.edu.usf.database.DBConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class TokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private TokenUtils() {
        throw new AssertionError("No " + TokenUtils.class + " instances for you!");
    }

    public static @Nullable String generateToken(@NotNull String userId) {
        Objects.requireNonNull(userId, "User ID can't be null");

        final Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection cannot be null");

        final String sql = "INSERT into tokens(user_id) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, UUID.fromString(userId));

            statement.execute();

            final ResultSet generatedKeys = statement.getGeneratedKeys();

            if (!generatedKeys.next()) {
                throw new RuntimeException("Token was not returned");
            }

            return generatedKeys.getString("token");

        } catch (SQLException | RuntimeException e) {
            logger.error("Error to produce and relate token", e);
        }

        return null;
    }

    public static boolean validateToken(@NotNull String token) {
        Objects.requireNonNull(token, "Token cannot be null");

        final Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection is null");

        final String sql = "SELECT * FROM tokens WHERE token = ? AND dt_expire > now()";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, UUID.fromString(token));

            if (!statement.execute()) {
                throw new SQLException("Error to execute query");
            }

            return statement.getResultSet().next();

        } catch (SQLException e) {
            logger.error("Error to produce and relate token", e);
        }

        return false;
    }

    public static boolean refreshTokenExpireDate(@NotNull String token) {
        Objects.requireNonNull(token, "Token can't be null");

        Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection is null");

        final String sql = "UPDATE tokens set dt_expire = now()::date + 30 WHERE token = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, token);

            statement.execute();

            return true;

        } catch (SQLException e) {
            logger.error("Error to refresh token expire date");
        }

        return false;
    }
}
