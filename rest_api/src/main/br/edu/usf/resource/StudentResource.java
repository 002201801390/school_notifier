package br.edu.usf.resource;

import br.edu.usf.dao.StudentDao;
import br.edu.usf.model.Student;

import javax.ws.rs.Path;
import java.util.Collection;

@Path("/student")
public class StudentResource extends DefaultResource<Student> {

    @Override
    public boolean insertImpl(Student student) {
        return StudentDao.gi().insert(student);
    }

    @Override
    public Collection<Student> findAllImpl() {
        return StudentDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(Student student) {
        return StudentDao.gi().update(student);
    }

    @Override
    public boolean deleteImpl(Student student) {
        return StudentDao.gi().update(student);
    }
}
