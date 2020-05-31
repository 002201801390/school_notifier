package br.edu.usf.model;

import br.edu.usf.utils.DaoUtils;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class LoggablePerson extends Person implements Loggable, Principal {

    public final String role = defineRole();

    protected String username;
    protected String password;

    public static LoggablePerson fromResultSet(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final String role = resultSet.getString("role");
        switch (role) {
            case "admin":
                return AdminEmployee.fromResultSetImpl(resultSet);

            case "employee":
                return Employee.fromResultSetImpl(resultSet);

            case "student":
                return Student.fromResultSetImpl(resultSet);

            case "responsible":
                return Responsible.fromResultSetImpl(resultSet);
        }
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected static void fillPersonDefaultImpl(ResultSet resultSet, LoggablePerson loggable) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");
        Objects.requireNonNull(loggable, "Person cannot be null");

        loggable.setId(resultSet.getString("id"));
        loggable.setName(resultSet.getString("name"));
        loggable.setCpf(resultSet.getString("cpf"));
        loggable.setDtBirth(DaoUtils.stringToLocalDate(resultSet.getString("dt_birth")));
        loggable.setEmail(resultSet.getString("email"));
        loggable.setPhone(resultSet.getString("phone"));
        loggable.setUsername(resultSet.getString("username"));
        loggable.setPassword(resultSet.getString("password"));
    }

    protected abstract String defineRole();
}