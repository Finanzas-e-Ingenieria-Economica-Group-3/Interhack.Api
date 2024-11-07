package interhack.api.users.application.queryServices;

import interhack.api.users.domain.model.entities.Role;
import interhack.api.users.domain.model.queries.GetAllRolesQuery;
import interhack.api.users.domain.model.queries.GetRoleByNameQuery;
import interhack.api.users.domain.services.RoleQueryService;
import interhack.api.users.infrastructure.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {
    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }

    public Optional<Role> handle(GetRoleByNameQuery query) {
        return roleRepository.findByName(query.name());
    }
}
