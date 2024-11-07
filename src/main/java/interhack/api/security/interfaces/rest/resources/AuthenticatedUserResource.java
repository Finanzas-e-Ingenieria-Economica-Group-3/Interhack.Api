package interhack.api.security.interfaces.rest.resources;

public record AuthenticatedUserResource(
        Long id,
        String login,
        String token
) { }
