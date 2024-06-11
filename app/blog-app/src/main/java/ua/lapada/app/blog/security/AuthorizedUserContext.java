package ua.lapada.app.blog.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AuthorizedUserContext {
    public static AuthorizedUser get() {
        return resolveUserFromSecurityContext()
                .orElseThrow(() -> new BadCredentialsException("Not Authorized"));
    }

    private static Optional<AuthorizedUser> resolveUserFromSecurityContext() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(AuthorizedUser.class::isInstance)
                .map(AuthorizedUser.class::cast);
    }
}
