package interhack.api.users.application.commandServices;

import interhack.api.users.domain.model.commands.SeedRolesCommand;
import interhack.api.users.domain.model.entities.Role;
import interhack.api.users.domain.model.valueObjets.Roles;
import interhack.api.users.domain.services.RoleCommandService;
import interhack.api.users.infrastructure.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role))
                roleRepository.save(new Role(Roles.valueOf(role.name())));
        });
    }
}
