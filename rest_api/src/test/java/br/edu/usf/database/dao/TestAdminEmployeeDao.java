package br.edu.usf.database.dao;

import br.edu.usf.dao.AdminEmployeeDao;
import br.edu.usf.model.AdminEmployee;
import br.edu.usf.utils.Randomization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class TestAdminEmployeeDao extends TestCrudDao {

    private static final AdminEmployee adminEmployee = new AdminEmployee();

    @BeforeAll
    public static void prepareAdminEmployee() {
        Randomization.fillWithRandomPerson(adminEmployee);
    }

    @Test
    @Order(1)
    @Override
    public void testCreate() {
        final boolean success = AdminEmployeeDao.gi().insert(adminEmployee);
        Assertions.assertTrue(success);

        final Collection<AdminEmployee> adminEmployees = AdminEmployeeDao.gi().findAll();
        Assertions.assertNotNull(adminEmployees);

        final boolean created = adminEmployees.stream().filter(s -> adminEmployee.getId().equals(s.getId())).count() == 1;
        Assertions.assertTrue(created);
    }

    @Test
    @Order(2)
    @Override
    public void testRead() {
        final boolean success = AdminEmployeeDao.gi().findAll() != null;
        Assertions.assertTrue(success);
    }

    @Test
    @Order(3)
    @Override
    public void testUpdate() {
        adminEmployee.setName("Updated Name" + adminEmployee.getName());

        final boolean success = AdminEmployeeDao.gi().update(adminEmployee);
        Assertions.assertTrue(success);
    }

    @Test
    @Order(4)
    @Override
    public void testDelete() {
        final boolean success = AdminEmployeeDao.gi().delete(adminEmployee);
        Assertions.assertTrue(success);
    }
}
