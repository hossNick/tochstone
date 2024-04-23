package com.train.touchstone.auth;

import com.train.touchstone.user.domain.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<AuthUser> getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                return principal instanceof AuthUser ? Optional.of(((AuthUser) principal)) : Optional.empty();
            }
        }
        return Optional.empty();
    }
}
