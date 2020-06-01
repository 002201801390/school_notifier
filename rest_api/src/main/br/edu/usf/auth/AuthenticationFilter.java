package br.edu.usf.auth;

import br.edu.usf.enums.ROLE;
import br.edu.usf.model.LoggablePerson;
import br.edu.usf.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final String AUTHENTICATION_SCHEME = "Token";
    private static final String MODULE_HEADER = "Module";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(@NotNull ContainerRequestContext requestContext) {
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        final String moduleHeader = requestContext.getHeaderString(MODULE_HEADER);

        final Class<?> resourceClass = resourceInfo.getResourceClass();
        final List<ROLE> classRoles = extractRoles(resourceClass);

        final Method resourceMethod = resourceInfo.getResourceMethod();
        final List<ROLE> methodRoles = extractRoles(resourceMethod);

        try {
            if (!isTokenBasedAuthentication(authHeader)) {
                throw new RuntimeException("Unauthorized!");
            }

            final String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            final String module = moduleHeader.substring(MODULE_HEADER.length()).trim();

            if (!TokenUtils.validateToken(token, module)) {
                throw new RuntimeException("Unauthorized!");
            }

            if (!TokenUtils.refreshTokenExpireDate(token)) {
                logger.warn("Cannot refresh token expire date");
            }

            final LoggablePerson userByToken = TokenUtils.getUserByToken(token);

            if (!methodRoles.isEmpty() && unauthorizedRole(methodRoles, userByToken)) {
                throw new RuntimeException("Role unauthorized!");
            }

            if (!classRoles.isEmpty() && unauthorizedRole(classRoles, userByToken)) {
                throw new RuntimeException("Role unauthorized!");
            }

            javax.ws.rs.core.SecurityContext securityContext = requestContext.getSecurityContext();

            requestContext.setSecurityContext(new br.edu.usf.auth.SecurityContext(securityContext, userByToken, AUTHENTICATION_SCHEME));

        } catch (Exception e) {
            unauthorized(requestContext);
        }
    }

    private boolean unauthorizedRole(List<ROLE> allowedRoles, LoggablePerson user) {
        Objects.requireNonNull(allowedRoles, "Allowed roles cannot be null");
        Objects.requireNonNull(user, "User cannot be null");

        return allowedRoles.stream().noneMatch(role -> role.toString().equalsIgnoreCase(user.role));
    }

    private List<ROLE> extractRoles(AnnotatedElement element) {
        if (element == null) {
            return new ArrayList<>();
        }

        final Secured secured = element.getAnnotation(Secured.class);
        if (secured == null) {
            return new ArrayList<>();
        }

        final ROLE[] allowedRoles = secured.value();
        return Arrays.asList(allowedRoles);
    }

    private static boolean isTokenBasedAuthentication(String authHeader) {
        return authHeader != null && authHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private static void unauthorized(@NotNull ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
