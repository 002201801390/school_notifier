package br.edu.usf.database.dao;

import br.edu.usf.dao.EmployeeDao;
import br.edu.usf.model.Employee;
import br.edu.usf.utils.Randomization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class TestEmployeeDao extends TestCrudDao {

    private static final Employee employee = new Employee();

    @BeforeAll
    public static void prepareEmployee() {
        Randomization.fillWithRandomPerson(employee);
    }

    @Test
    @Order(1)
    @Override
    public void testCreate() {
        final boolean success = EmployeeDao.gi().insert(employee);
        Assertions.assertTrue(success);

        final Collection<Employee> employees = EmployeeDao.gi().findAll();
        Assertions.assertNotNull(employees);

        final boolean created = employees.stream().filter(s -> employee.getId().equals(s.getId())).count() == 1;
        Assertions.assertTrue(created);
    }

    @Test
    @Order(2)
    @Override
    public void testRead() {
        final boolean success = EmployeeDao.gi().findAll() != null;
        Assertions.assertTrue(success);
    }

    @Test
    @Order(3)
    @Override
    public void testUpdate() {
        employee.setName("Updated Name" + employee.getName());

        final boolean success = EmployeeDao.gi().update(employee);
        Assertions.assertTrue(success);
    }

    @Test
    @Order(4)
    @Override
    public void testDelete() {
        final boolean success = EmployeeDao.gi().delete(employee);
        Assertions.assertTrue(success);
    }
}
