package br.edu.usf.database.dao;

import br.edu.usf.dao.StudentDao;
import br.edu.usf.model.Student;
import br.edu.usf.utils.Randomization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class TestStudentDao extends TestCrudDao {

    private static final Student student = new Student();

    @BeforeAll
    public static void prepareStudent() {
        Randomization.fillWithRandomPerson(student);
    }

    @Test
    @Order(1)
    @Override
    public void testCreate() {
        final boolean success = StudentDao.gi().insert(student);
        Assertions.assertTrue(success);

        final Collection<Student> students = StudentDao.gi().findAll();
        Assertions.assertNotNull(students);

        final boolean created = students.stream().filter(s -> student.getId().equals(s.getId())).count() == 1;
        Assertions.assertTrue(created);
    }

    @Test
    @Order(2)
    @Override
    public void testRead() {
        final boolean success = StudentDao.gi().findAll() != null;
        Assertions.assertTrue(success);
    }

    @Test
    @Order(3)
    @Override
    public void testUpdate() {
        student.setName("Updated Name" + student.getName());

        final boolean success = StudentDao.gi().update(student);
        Assertions.assertTrue(success);
    }

    @Test
    @Order(4)
    @Override
    public void testDelete() {
        final boolean success = StudentDao.gi().delete(student);
        Assertions.assertTrue(success);
    }
}
