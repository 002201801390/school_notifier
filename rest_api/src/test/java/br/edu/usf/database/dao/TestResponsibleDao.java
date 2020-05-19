package br.edu.usf.database.dao;

import br.edu.usf.dao.ResponsibleDao;
import br.edu.usf.model.Responsible;
import br.edu.usf.utils.Randomization;
import org.junit.jupiter.api.*;

import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestResponsibleDao extends TestCrudDao {

    private static final Responsible responsible = new Responsible();

    @BeforeAll
    public static void prepareResponsible() {
        Randomization.fillWithRandomPerson(responsible);
    }

    @Test
    @Order(1)
    @Override
    public void testCreate() {
        final boolean success = ResponsibleDao.gi().insert(responsible);
        Assertions.assertTrue(success);

        final Collection<Responsible> responsibles = ResponsibleDao.gi().findAll();
        Assertions.assertNotNull(responsibles);

        final boolean created = responsibles.stream().filter(s -> responsible.getId().equals(s.getId())).count() == 1;
        Assertions.assertTrue(created);
    }

    @Test
    @Order(2)
    @Override
    public void testRead() {
        final boolean success = ResponsibleDao.gi().findAll() != null;
        Assertions.assertTrue(success);
    }

    @Test
    @Order(3)
    @Override
    public void testUpdate() {
        responsible.setName("Updated Name" + responsible.getName());

        final boolean success = ResponsibleDao.gi().update(responsible);
        Assertions.assertTrue(success);
    }

    @Test
    @Order(4)
    @Override
    public void testDelete() {
        final boolean success = ResponsibleDao.gi().delete(responsible);
        Assertions.assertTrue(success);
    }
}
