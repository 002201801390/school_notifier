package br.edu.usf.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/auth")
public class AuthenticationEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEndpoint.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {

        if (authenticate(username, password)) {
            logger.debug("User authenticated");

            String token = issueToken();

            return Response.ok(token).build();
        }

        logger.debug("Invalid credentials");
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    private boolean authenticate(String username, String password) {
        // TODO
        return false;
    }

    private String issueToken() {
        // TODO
        return UUID.randomUUID().toString();
    }
}
