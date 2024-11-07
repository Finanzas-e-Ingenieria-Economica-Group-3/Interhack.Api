package interhack.api.security.interfaces.rest.transform;

import interhack.api.security.domain.model.commands.SignInCommand;
import interhack.api.security.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.email(), signInResource.password());
    }
}
