package br.edu.usf.resource;

import br.edu.usf.auth.Secured;
import br.edu.usf.dao.ReportCardDao;
import br.edu.usf.enums.ROLE;
import br.edu.usf.model.LoggablePerson;
import br.edu.usf.model.ReportCard;
import br.edu.usf.model.Responsible;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;

@Path("/report_card")
public class ReportCardResource extends DefaultResource<ReportCard> {
    @Override
    public boolean insertImpl(ReportCard reportCard) {
        return ReportCardDao.gi().insert(reportCard);
    }

    @Override
    public Collection<ReportCard> findAllImpl() {
        return ReportCardDao.gi().findAll();
    }

    @Override
    public boolean updateImpl(ReportCard reportCard) {
        return ReportCardDao.gi().update(reportCard);
    }

    @Override
    public boolean deleteImpl(ReportCard reportCard) {
        return ReportCardDao.gi().delete(reportCard);
    }

    @Override
    protected ReportCard convertInputImpl(String input) {
        return ReportCard.fromJson(input);
    }

    @GET
    @Secured({ROLE.ADMIN, ROLE.EMPLOYEE, ROLE.RESPONSIBLE})
    @Path("/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response myStudentsReportCard(@Context SecurityContext context) {
        final LoggablePerson userPrincipal = (LoggablePerson) context.getUserPrincipal();
        final Collection<ReportCard> reportCards;

        switch (userPrincipal.role) {
            case "admin":
            case "employee":
                reportCards = ReportCardDao.gi().findAll();
                break;
            case "responsible":
                reportCards = ReportCardDao.gi().relatedTo((Responsible) userPrincipal);
                break;
            default:
                return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(reportCards).build();
    }
}
