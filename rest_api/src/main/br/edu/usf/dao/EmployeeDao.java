package br.edu.usf.dao;

import br.edu.usf.model.Employee;
import br.edu.usf.utils.UserDaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDao extends UserDao<Employee> {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);

    private static final EmployeeDao instance = new EmployeeDao();

    public static EmployeeDao gi() {
        return instance;
    }

    @Override
    protected Employee resultSetToPerson(ResultSet resultSet) throws SQLException {
        final Employee employee = new Employee();

        UserDaoUtils.resultSetToPerson(resultSet, employee);

        return employee;
    }

    @Override
    public String role() {
        return "employee";
    }
}
