package interhack.api.users.interfaces.rest.transform;

import interhack.api.users.domain.model.aggregates.User;
import interhack.api.users.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(role -> role.getName().name()).toList();
        return new UserResource(user.getId(), user.getUsername(), user.getEmail(), roles);
    }
}
