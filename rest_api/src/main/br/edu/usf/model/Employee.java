package br.edu.usf.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Employee extends LoggablePerson {

    public static Employee fromResultSetImpl(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final Employee employee = new Employee();
        fillPersonDefaultImpl(resultSet, employee);
        return employee;
    }

    @Override
    protected String defineRole() {
        return "employee";
    }
}
