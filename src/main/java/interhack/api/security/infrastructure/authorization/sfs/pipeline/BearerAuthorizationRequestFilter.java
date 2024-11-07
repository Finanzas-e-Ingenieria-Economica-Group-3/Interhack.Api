package interhack.api.security.infrastructure.authorization.sfs.pipeline;

import interhack.api.security.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import interhack.api.security.infrastructure.tokens.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Clase que se encarga de obtener, validar el token y los filtros del usuario por cada petición que se realiza
 * @author Leonardo Lopzez
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);
    private final BearerTokenService tokenService;
    @Qualifier("defaultUserDetailsService")
    private final UserDetailsService userDetailsService;

    public BearerAuthorizationRequestFilter(BearerTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Realiza el filtro de autenticación
     * @param request Solicitud http
     * @param response Respuesta http
     * @param filterChain Cadena de filtros
     * @throws ServletException Excepción de servlet
     * @throws IOException Excepción de entrada/salida
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenService.getBearerTokenFrom(request);
            LOGGER.info("Token: {}", token);
            if (token != null && tokenService.validateToken(token)) {
                String username = tokenService.getUsernameFromToken(token);
                var userDetails = userDetailsService.loadUserByUsername(username);

                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationTokenBuilder
                        .build(userDetails, request));
            } else {
                LOGGER.warn("Token is not valid");
            }
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication: {}", e.getMessage());
        }

        //continue with the filter chain
        filterChain.doFilter(request, response);
    }
}