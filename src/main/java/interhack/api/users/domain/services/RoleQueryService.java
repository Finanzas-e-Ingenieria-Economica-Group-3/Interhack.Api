package interhack.api.users.domain.services;

import interhack.api.users.domain.model.entities.Role;
import interhack.api.users.domain.model.queries.GetAllRolesQuery;
import interhack.api.users.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);

}
