package com.cheroliv.core.security

import com.cheroliv.core.config.AuthoritiesConstants
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

import java.util.stream.Stream

final class SecurityUtils {


/**
 * Get the login of the current user.
 *
 * @return the login of the current user.
 */
    static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext()
        return Optional.ofNullable(securityContext.getAuthentication())
                .map({ authentication ->
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal()
                        return springSecurityUser.getUsername()
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal()
                    }
                    return null
                })
    }

/**
 * Get the JWT of the current user.
 *
 * @return the JWT of the current user.
 */
    static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext()
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter({ authentication -> authentication.getCredentials() instanceof String })
                .map({ authentication -> (String) authentication.getCredentials() })
    }

/**
 * Check if a user is authenticated.
 *
 * @return true if the user is authenticated, false otherwise.
 */
    static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        return authentication != null &&
                getAuthorities(authentication).noneMatch({ anObject -> AuthoritiesConstants.ANONYMOUS.equals(anObject) })
    }

/**
 * If the current user has a specific authority (security role).
 * <p>
 * The name of this method comes from the {@code isUserInRole( )} method in the Servlet API.
 *
 * @param authority the authority to check.
 * @return true if the current user has the authority, false otherwise.
 */
    static boolean isCurrentUserInRole(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        return authentication != null &&
                getAuthorities(authentication).anyMatch({ anObject -> authority.equals(anObject) })
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map({ grantedAuthority -> grantedAuthority.getAuthority() })
    }

}
