package br.edu.usf.auth;

import br.edu.usf.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Token";

    @Override
    public void filter(@NotNull ContainerRequestContext requestContext) throws IOException {
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            if (!isTokenBasedAuthentication(authHeader)) {
                throw new RuntimeException("Unauthorized!");
            }

            final String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

            if (!validToken(token)) {
                throw new RuntimeException("Unauthorized!");
            }

        } catch (Exception e) {
            unauthorized(requestContext);
        }
    }

    private static boolean isTokenBasedAuthentication(String authHeader) {
        return authHeader != null && authHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private static boolean validToken(String token) {
        return TokenUtils.validateToken(token);
    }

    private static void unauthorized(@NotNull ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
