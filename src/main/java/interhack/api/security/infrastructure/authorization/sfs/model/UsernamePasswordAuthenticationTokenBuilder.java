package interhack.api.security.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


/**
 * Clase que se encarga de construir un UsernamePasswordAuthenticationToken
 * a partir de un usuario y una petición HTTP
 * @autor Leonardo Lopez
 */
public class UsernamePasswordAuthenticationTokenBuilder {
    public static UsernamePasswordAuthenticationToken build(
            UserDetails principal,
            HttpServletRequest request
    ) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }
}
