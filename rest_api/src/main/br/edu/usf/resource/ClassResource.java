package br.edu.usf.resource;

import br.edu.usf.dao.ClassDao;
import br.edu.usf.model.Class;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("class")
public class ClassResource extends DefaultResource<Class> {
    @Override
    public boolean insertImpl(Class aClass) {
        return ClassDao.gi().insert(aClass);
    }

    @Override
    public Collection<Class> findAllImpl() {
        return ClassDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(Class aClass) {
        return ClassDao.gi().update(aClass);
    }

    @Override
    public boolean deleteImpl(Class aClass) {
        return ClassDao.gi().delete(aClass);
    }

    @Override
    protected Class convertInput(String input) {
        return Class.fromJson(input);
    }
}
