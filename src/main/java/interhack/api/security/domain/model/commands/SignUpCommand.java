package interhack.api.security.domain.model.commands;

import interhack.api.users.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(
        String username,
        String email,
        String password,
        List<Role> roles) {
}
