package interhack.api.security.domain.model.commands;

public record SignInCommand(
        String email,
        String password
) { }
