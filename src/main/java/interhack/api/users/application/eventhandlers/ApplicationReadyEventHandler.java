package interhack.api.users.application.eventhandlers;

import interhack.api.users.domain.model.commands.SeedRolesCommand;
import interhack.api.users.domain.services.RoleCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class ApplicationReadyEventHandler {
    private final RoleCommandService roleCommandService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);
    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event){
        var name = event.getApplicationContext().getId();
        LOGGER.info("Started role seeding for {} at {}", name, new Timestamp(System.currentTimeMillis()));
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
        LOGGER.info("Roles seeded successfully for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }
}