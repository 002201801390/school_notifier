package br.edu.usf.dao;

import br.edu.usf.model.AdminEmployee;
import br.edu.usf.utils.UserDaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminEmployeeDao extends UserDao<AdminEmployee> {
    private static final Logger logger = LoggerFactory.getLogger(AdminEmployeeDao.class);

    private static final AdminEmployeeDao instance = new AdminEmployeeDao();

    public static AdminEmployeeDao gi() {
        return instance;
    }

    @Override
    protected AdminEmployee resultSetToPerson(ResultSet resultSet) throws SQLException {
        final AdminEmployee adminEmployee = new AdminEmployee();

        UserDaoUtils.resultSetToPerson(resultSet, adminEmployee);

        return adminEmployee;
    }

    @Override
    public String role() {
        return "admin";
    }
}
