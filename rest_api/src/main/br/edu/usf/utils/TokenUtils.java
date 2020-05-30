package br.edu.usf.utils;

import br.edu.usf.database.DBConnection;
import br.edu.usf.model.LoggablePerson;
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

    public static @Nullable String getExistingTokenOrCreateOne(String userId, String module) {
        Objects.requireNonNull(userId, "User ID can't be null");
        Objects.requireNonNull(module, "Module can't be null");

        final String existingToken = getExistingToken(userId, module);
        if (InputUtils.validString(existingToken)) {

            return existingToken;
        }
        return generateToken(userId, module);
    }

    public static @Nullable String getExistingToken(String userId, String module) {
        Objects.requireNonNull(userId, "User ID can't be null");
        Objects.requireNonNull(module, "Module can't be null");

        final Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection cannot be null");

        final String sql = "SELECT token FROM tokens WHERE user_id = ? AND module = '" + module + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, UUID.fromString(userId));

            if (!statement.execute()) {
                throw new RuntimeException("Token not found for user");
            }

            final ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException | RuntimeException e) {
            logger.error("Error to search token from user");
        }
        return null;
    }

    public static @Nullable String generateToken(String userId, String module) {
        Objects.requireNonNull(userId, "User ID can't be null");
        Objects.requireNonNull(module, "Module can't be null");

        final Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection cannot be null");

        final String sql = "INSERT INTO tokens(user_id, module) VALUES(?, '" + module + "')";

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

    public static boolean validateToken(String token, String module) {
        Objects.requireNonNull(token, "Token cannot be null");
        Objects.requireNonNull(module, "Module cannot be null");

        final Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection is null");

        final String sql = "SELECT * FROM tokens WHERE token = ? AND dt_expire > now() AND module = '" + module + "'";

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

    public static boolean refreshTokenExpireDate(String token) {
        Objects.requireNonNull(token, "Token can't be null");

        Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection is null");

        final String sql = "UPDATE tokens set dt_expire = now()::date + 30 WHERE token = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, UUID.fromString(token));

            statement.execute();

            return true;

        } catch (SQLException e) {
            logger.error("Error to refresh token expire date");
        }

        return false;
    }

    public static LoggablePerson getUserByToken(String token) {
        Objects.requireNonNull(token, "Token can't be null");

        Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection is null");

        final String sql = "SELECT * FROM users u INNER JOIN tokens t ON u.id = t.user_id WHERE token = ? ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, UUID.fromString(token));

            statement.execute();

            final ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return LoggablePerson.fromResultSet(resultSet);
            }

        } catch (SQLException e) {
            logger.error("Error to refresh token expire date");
        }
        return null;
    }

    public static void cleanExpiredTokens() {
        Connection connection = DBConnection.gi().connection();

        Objects.requireNonNull(connection, "Database connection is null");

        final String sql = "DELETE FROM tokens WHERE dt_expire < now()";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();

        } catch (SQLException e) {
            logger.error("Error to refresh token expire date");
        }
    }
}
