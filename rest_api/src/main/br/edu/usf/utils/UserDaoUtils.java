package br.edu.usf.utils;

import br.edu.usf.model.LoggablePerson;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDaoUtils {
    private UserDaoUtils() {
        throw new AssertionError("No " + UserDaoUtils.class + " instances for you!");
    }

    public static String insertQuery(String role) {
        return "INSERT INTO users (cpf, name, username, password, email, dt_birth, phone, role) VALUES(?, ?, ?, ?, ?, ?, ?, '" + role + "')";
    }

    public static String selectQuery(String role) {
        return "SELECT id, cpf, name, username, password, email, dt_birth, phone FROM " + role + " ";
    }

    public static String selectQuery(String role, String userId) {
        return "SELECT id, cpf, name, username, password, email, dt_birth, phone FROM " + role + " WHERE id = '" + userId + "' ";
    }

    public static String selectQuery(String role, Collection<String> userId) {
        final String ids = userId.stream().map(i -> "'" + i + "'").collect(Collectors.joining(", "));
        return "SELECT id, cpf, name, username, password, email, dt_birth, phone FROM " + role + " WHERE id IN(" + ids + ") ";
    }

    public static String updateQuery() {
        return "UPDATE users set cpf = ?, name = ?, username = ?, password = ?, email = ?, dt_birth = ?, phone = ? WHERE id = ? ";
    }

    public static String deleteQuery() {
        return "DELETE FROM users WHERE id = ? ";
    }

    public static void resultSetToPerson(ResultSet resultSet, LoggablePerson person) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null!");
        Objects.requireNonNull(person, "Person cannot be null!");

        person.setId(resultSet.getString("id"));
        person.setCpf(resultSet.getString("cpf"));
        person.setName(resultSet.getString("name"));
        person.setUsername(resultSet.getString("username"));
        person.setPassword(resultSet.getString("password"));
        person.setEmail(resultSet.getString("email"));
        person.setPhone(resultSet.getString("phone"));

        Date dtBirth = resultSet.getDate("dt_birth");
        if (dtBirth != null) {
            person.setDtBirth(dtBirth.toLocalDate());
        }
    }
}
