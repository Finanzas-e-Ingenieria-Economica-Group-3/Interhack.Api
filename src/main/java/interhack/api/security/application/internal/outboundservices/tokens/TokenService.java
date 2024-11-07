package interhack.api.security.application.internal.outboundservices.tokens;

public interface TokenService {
    String generateToken(String email);
    String getUsernameFromToken(String token);
    boolean validateToken(String token);
}
