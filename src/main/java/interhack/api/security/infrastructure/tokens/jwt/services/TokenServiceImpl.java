package interhack.api.security.infrastructure.tokens.jwt.services;

import interhack.api.security.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Clase de utilidades para la seguridad y autenticación de usuarios
 * @author Leonardo Lopez
 */
@Service
public class TokenServiceImpl implements BearerTokenService {
    private final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
    private static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_BEGIN_INDEX = BEARER_TOKEN_PREFIX.length();

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("3")
    private int expirationDays;


    /**
     * Convierte el secret en una clave secreta usando HMAC SHA
     * @return Clave secreta para firmar el token
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Construye un token con los parámetros por defecto
     * @param email Email del usuario
     * @return Token construido
     */
    public String generateToken(String email) {
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        /*var expirationMinutes = DateUtils.addMinutes(expiration, this.expirationMinutes);*/
        var key = getSigningKey();
        return Jwts.builder()
                .subject(email)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }


    @Override
    public String generateToken(Authentication authentication) {
        var authenticatedUser = (User) authentication.getPrincipal();
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSigningKey();
        return Jwts.builder()
                .subject(authenticatedUser.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Obtiene el ID del usuario a partir del token
     * @param token Token a procesar
     * @return ID del usuario
     */
    @Override
    public String getUsernameFromToken(String token) {
        // Parsear el token utilizando la clave secreta
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(getSigningKey()) // Verificar la firma del token
                .build() // Construir el parser JWT
                .parseSignedClaims(token) // Parsear el token firmado
                .getPayload(); // Obtener el payload (claims) del token

        // Obtener el 'subject' del token, que típicamente es el nombre de usuario o ID
        return claims.getSubject();
    }

    /**
     * Valida el token
     * @param token Token a validar
     * @return True = token válido
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            LOGGER.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JSON Web Token Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JSON Web Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JSON Web Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JSON Web Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JSON Web Token claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene el token garantizando el uso del Bearer de la solicitud
     * @param request Solicitud http
     * @return Token Bearer
     */
    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String parameter = getAuthorizationParameterFrom(request);
        if (isTokenPresentIn(parameter) && isBearerTokenIn(parameter))
            return extractTokenFrom(parameter);
        return null;
    }
    // Extracted methods
    private String getAuthorizationParameterFrom(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_PARAMETER_NAME);
    }

    // Validate token
    private boolean isTokenPresentIn(String authorizationParameter) {
        return StringUtils.hasText(authorizationParameter);
    }

    // Validate Bearer token
    private boolean isBearerTokenIn(String authorizationParameter) {
        return authorizationParameter.startsWith(BEARER_TOKEN_PREFIX);
    }

    // Extract token
    private String extractTokenFrom(String authorizationParameter) {
        return authorizationParameter.substring(TOKEN_BEGIN_INDEX);
    }

}