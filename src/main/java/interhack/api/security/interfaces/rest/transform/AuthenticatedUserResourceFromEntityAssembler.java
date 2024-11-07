package interhack.api.security.interfaces.rest.transform;

import interhack.api.security.interfaces.rest.resources.AuthenticatedUserResource;
import interhack.api.users.domain.model.aggregates.User;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getEmail(), token);
    }
}
