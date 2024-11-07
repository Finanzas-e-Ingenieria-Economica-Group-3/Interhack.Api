package interhack.api.users.interfaces.rest.transform;

import interhack.api.users.domain.model.entities.Role;
import interhack.api.users.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
