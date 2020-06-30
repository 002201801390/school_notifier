package br.edu.usf.resource;

import br.edu.usf.auth.Secured;
import br.edu.usf.dao.ResponsibleDao;
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

@Path("/responsible")
public class ResponsibleResource extends DefaultResource<Responsible> {

    @Override
    public boolean insertImpl(Responsible responsible) {
        return ResponsibleDao.gi().insert(responsible);
    }

    @Override
    public Collection<Responsible> findAllImpl() {
        return ResponsibleDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(Responsible responsible) {
        return ResponsibleDao.gi().update(responsible);
    }

    @Override
    public boolean deleteImpl(Responsible responsible) {
        return ResponsibleDao.gi().delete(responsible);
    }

    @Override
    protected Responsible convertInputImpl(String input) {
        return Responsible.fromJson(input);
    }

    @POST
    @Secured({ROLE.ADMIN, ROLE.EMPLOYEE, ROLE.STUDENT})
    @Path("/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response myStudents(@Context SecurityContext context) {
        final LoggablePerson userPrincipal = (LoggablePerson) context.getUserPrincipal();
        final Collection<Responsible> responsible;

        switch (userPrincipal.role) {
            case "admin":
            case "employee":
                responsible = ResponsibleDao.gi().findAll();
                break;
            case "student":
                responsible = ResponsibleDao.gi().relatedTo((Student) userPrincipal);
                break;
            default:
                return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(responsible).build();
    }
}
