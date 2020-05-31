package br.edu.usf.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AdminEmployee extends Employee {

    public static AdminEmployee fromResultSetImpl(ResultSet resultSet) throws SQLException {
        Objects.requireNonNull(resultSet, "ResultSet cannot be null");

        final AdminEmployee adminEmployee = new AdminEmployee();
        fillPersonDefaultImpl(resultSet, adminEmployee);
        return adminEmployee;
    }

    @Override
    protected String defineRole() {
        return "admin";
    }
}
