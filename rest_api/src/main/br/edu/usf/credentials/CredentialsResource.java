package br.edu.usf.credentials;

import br.edu.usf.auth.Secured;
import br.edu.usf.model.LoggablePerson;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/credentials")
public class CredentialsResource {

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCredentials(@Context SecurityContext securityContext) {
        final LoggablePerson userPrincipal = (LoggablePerson) securityContext.getUserPrincipal();
        return Response.ok(userPrincipal).build();
    }
}
