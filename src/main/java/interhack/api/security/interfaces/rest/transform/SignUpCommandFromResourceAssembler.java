package interhack.api.security.interfaces.rest.transform;

import interhack.api.security.domain.model.commands.SignUpCommand;
import interhack.api.security.interfaces.rest.resources.SignUpResource;
import interhack.api.users.domain.model.entities.Role;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource signUpResource) {
        var roles = signUpResource.roles() != null ? signUpResource.roles().stream()
                .map(Role::toRoleFromName)
                .toList() : new ArrayList<Role>();
        return new SignUpCommand(signUpResource.username(), signUpResource.email(), signUpResource.password(), roles);
    }
}
