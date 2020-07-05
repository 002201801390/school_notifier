package br.edu.usf.utils;

import br.edu.usf.model.LoggablePerson;
import org.jetbrains.annotations.NotNull;

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
        switch (role) {
            case "student":
                return selectQueryStudent();
            case "responsible":
                return selectQueryResponsible();
            default:
                return defaultSelectQuery(role);
        }
    }

    public static String selectQuery(String role, String userId) {
        switch (role) {
            case "student":
                return selectQueryStudent(userId);
            case "responsible":
                return selectQueryResponsible(userId);
            default:
                return defaultSelectQuery(role, userId);
        }
    }

    public static String selectQuery(String role, Collection<String> userId) {
        switch (role) {
            case "student":
                return selectQueryStudent(userId);
            case "responsible":
                return selectQueryResponsible(userId);
            default:
                return defaultSelectQuery(role, userId);
        }
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

    private static String defaultSelectQuery(String role) {
        return "SELECT id, cpf, name, username, password, email, dt_birth, phone FROM " + role + " ";
    }

    private static String defaultSelectQuery(String role, String userId) {
        return "SELECT id, cpf, name, username, password, email, dt_birth, phone FROM " + role + " WHERE id = '" + userId + "' ";
    }

    private static String defaultSelectQuery(String role, Collection<String> userId) {
        final String ids = formatIDsToQuery(userId);
        return "SELECT id, cpf, name, username, password, email, dt_birth, phone FROM " + role + " WHERE id IN(" + ids + ") ";
    }

    private static String selectQueryStudent() {
        return "SELECT " +
                "s.id            AS id, " +
                "s.cpf           AS cpf, " +
                "s.name          AS name, " +
                "s.username      AS username, " +
                "s.password      AS password, " +
                "s.email         AS email, " +
                "s.dt_birth      AS dt_birth, " +
                "s.phone         AS phone, " +
                "array_agg(r.id) AS responsible_ids " +
                "FROM responsible_student rs " +
                "INNER JOIN responsible r on rs.responsible_id = r.id " +
                "INNER JOIN student s on rs.student_id = s.id " +
                "GROUP BY s.id, s.cpf, s.name, s.username, s.password, s.email, s.dt_birth, s.phone ";
    }

    private static String selectQueryStudent(String userId) {
        return "SELECT " +
                "s.id            AS id, " +
                "s.cpf           AS cpf, " +
                "s.name          AS name, " +
                "s.username      AS username, " +
                "s.password      AS password, " +
                "s.email         AS email, " +
                "s.dt_birth      AS dt_birth, " +
                "s.phone         AS phone, " +
                "array_agg(r.id) AS responsible_ids " +
                "FROM responsible_student rs " +
                "INNER JOIN responsible r on rs.responsible_id = r.id " +
                "INNER JOIN student s on rs.student_id = s.id " +
                "WHERE s.id = '" + userId + "' " +
                "GROUP BY s.id, s.cpf, s.name, s.username, s.password, s.email, s.dt_birth, s.phone ";
    }

    private static String selectQueryStudent(Collection<String> userId) {
        final String ids = formatIDsToQuery(userId);
        return "SELECT " +
                "s.id            AS id, " +
                "s.cpf           AS cpf, " +
                "s.name          AS name, " +
                "s.username      AS username, " +
                "s.password      AS password, " +
                "s.email         AS email, " +
                "s.dt_birth      AS dt_birth, " +
                "s.phone         AS phone, " +
                "array_agg(r.id) AS responsible_ids " +
                "FROM responsible_student rs " +
                "INNER JOIN responsible r on rs.responsible_id = r.id " +
                "INNER JOIN student s on rs.student_id = s.id " +
                "WHERE s.id IN (" + ids + ") " +
                "GROUP BY s.id, s.cpf, s.name, s.username, s.password, s.email, s.dt_birth, s.phone ";
    }

    private static String selectQueryResponsible() {
        return "SELECT " +
                "r.id            AS id, " +
                "r.cpf           AS cpf, " +
                "r.name          AS name, " +
                "r.username      AS username, " +
                "r.password      AS password, " +
                "r.email         AS email, " +
                "r.dt_birth      AS dt_birth, " +
                "r.phone         AS phone, " +
                "array_agg(s.id) AS student_ids " +
                "FROM responsible_student rs " +
                "INNER JOIN responsible r on rs.responsible_id = r.id " +
                "INNER JOIN student s on rs.student_id = s.id " +
                "GROUP BY r.id, r.cpf, r.name, r.username, r.password, r.email, r.dt_birth, r.phone ";
    }

    private static String selectQueryResponsible(String userId) {
        return "SELECT " +
                "r.id            AS id, " +
                "r.cpf           AS cpf, " +
                "r.name          AS name, " +
                "r.username      AS username, " +
                "r.password      AS password, " +
                "r.email         AS email, " +
                "r.dt_birth      AS dt_birth, " +
                "r.phone         AS phone, " +
                "array_agg(s.id) AS student_ids " +
                "FROM responsible_student rs " +
                "INNER JOIN responsible r on rs.responsible_id = r.id " +
                "INNER JOIN student s on rs.student_id = s.id " +
                "WHERE id = '" + userId + "' " +
                "GROUP BY r.id, r.cpf, r.name, r.username, r.password, r.email, r.dt_birth, r.phone ";
    }

    private static String selectQueryResponsible(Collection<String> userId) {
        final String ids = formatIDsToQuery(userId);
        return "SELECT " +
                "r.id            AS id, " +
                "r.cpf           AS cpf, " +
                "r.name          AS name, " +
                "r.username      AS username, " +
                "r.password      AS password, " +
                "r.email         AS email, " +
                "r.dt_birth      AS dt_birth, " +
                "r.phone         AS phone, " +
                "array_agg(s.id) AS student_ids " +
                "FROM responsible_student rs " +
                "INNER JOIN responsible r on rs.responsible_id = r.id " +
                "INNER JOIN student s on rs.student_id = s.id " +
                "WHERE id IN(" + ids + ") " +
                "GROUP BY r.id, r.cpf, r.name, r.username, r.password, r.email, r.dt_birth, r.phone ";
    }

    @NotNull
    private static String formatIDsToQuery(Collection<String> userId) {
        return userId.stream().map(i -> "'" + i + "'").collect(Collectors.joining(", "));
    }
}
