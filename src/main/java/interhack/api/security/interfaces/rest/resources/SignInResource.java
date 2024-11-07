package interhack.api.security.interfaces.rest.resources;

public record SignInResource(
        String email,
        String password
) { }
