package br.edu.usf.resource;

import br.edu.usf.dao.DisciplineDao;
import br.edu.usf.model.Discipline;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("/discipline")
public class DisciplineResource extends DefaultResource<Discipline> {
    @Override
    public boolean insertImpl(Discipline discipline) {
        return DisciplineDao.gi().insert(discipline);
    }

    @Override
    public Collection<Discipline> findAllImpl() {
        return DisciplineDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(Discipline discipline) {
        return DisciplineDao.gi().update(discipline);
    }

    @Override
    public boolean deleteImpl(Discipline discipline) {
        return DisciplineDao.gi().delete(discipline);
    }

    @Override
    protected Discipline convertInputImpl(String input) {
        return Discipline.fromJson(input);
    }
}
