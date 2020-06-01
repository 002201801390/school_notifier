package br.edu.usf.model;

import com.google.gson.Gson;

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

    public static Employee fromJson(String json) {
        return new Gson().fromJson(json, Employee.class);
    }

    @Override
    protected String defineRole() {
        return "employee";
    }
}
