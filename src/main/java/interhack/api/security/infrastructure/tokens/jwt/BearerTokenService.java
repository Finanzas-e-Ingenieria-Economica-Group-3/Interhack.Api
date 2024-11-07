package interhack.api.security.infrastructure.tokens.jwt;

import interhack.api.security.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {
    String generateToken(Authentication authentication);

    String getBearerTokenFrom(HttpServletRequest request);
}