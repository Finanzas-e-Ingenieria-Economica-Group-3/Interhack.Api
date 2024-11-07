package interhack.api.users.domain.services;

import interhack.api.users.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
