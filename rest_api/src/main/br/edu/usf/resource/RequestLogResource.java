package br.edu.usf.resource;

import br.edu.usf.auth.Secured;
import br.edu.usf.database.MongoConnection;
import br.edu.usf.enums.ROLE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/log_request")
public class RequestLogResource {

    @GET
    @Secured({ROLE.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoggedRequests() {
        return Response.ok(MongoConnection.gi().getLogRequests()).build();
    }
}
