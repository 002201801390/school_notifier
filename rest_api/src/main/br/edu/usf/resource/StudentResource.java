package br.edu.usf.resource;

import br.edu.usf.auth.Secured;
import br.edu.usf.dao.StudentDao;
import br.edu.usf.enums.ROLE;
import br.edu.usf.model.LoggablePerson;
import br.edu.usf.model.Responsible;
import br.edu.usf.model.Student;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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
        return StudentDao.gi().delete(student);
    }

    @Override
    protected Student convertInputImpl(String input) {
        return Student.fromJson(input);
    }

    @POST
    @Secured({ROLE.ADMIN, ROLE.EMPLOYEE, ROLE.RESPONSIBLE})
    @Path("/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response myStudents(@Context SecurityContext context) {
        final LoggablePerson userPrincipal = (LoggablePerson) context.getUserPrincipal();
        final Collection<Student> students;

        switch (userPrincipal.role) {
            case "admin":
            case "employee":
                students = StudentDao.gi().findAll();
                break;
            case "responsible":
                students = StudentDao.gi().relatedTo((Responsible) userPrincipal);
                break;
            default:
                return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(students).build();
    }

}
