package br.edu.usf.auth;

import br.edu.usf.model.LoggablePerson;

import java.util.Objects;

public class SecurityContext implements javax.ws.rs.core.SecurityContext {

    private final javax.ws.rs.core.SecurityContext baseContext;
    private final LoggablePerson loggablePerson;
    private final String authScheme;

    public SecurityContext(javax.ws.rs.core.SecurityContext securityContext, LoggablePerson loggablePerson, String authScheme) {
        Objects.requireNonNull(securityContext, "Base secured context cannot be null");
        Objects.requireNonNull(loggablePerson, "Loggable Person cannot be null");
        Objects.requireNonNull(authScheme, "Authentication Scheme cannot be null");

        this.baseContext = securityContext;
        this.loggablePerson = loggablePerson;
        this.authScheme = authScheme;
    }

    @Override
    public LoggablePerson getUserPrincipal() {
        return loggablePerson;
    }

    @Override
    public boolean isUserInRole(String role) {
        return role.equalsIgnoreCase(loggablePerson.role);
    }

    @Override
    public boolean isSecure() {
        return baseContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        return authScheme;
    }
}
