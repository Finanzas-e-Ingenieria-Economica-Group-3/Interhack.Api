package interhack.api.users.interfaces.rest;

import interhack.api.users.domain.model.queries.GetAllUsersQuery;
import interhack.api.users.domain.model.queries.GetUserByIdQuery;
import interhack.api.users.domain.services.UserQueryService;
import interhack.api.users.interfaces.rest.resources.UserResource;
import interhack.api.users.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;

    public UsersController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.listUser(getAllUsersQuery);
        var userResources = users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId){
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.userId(getUserByIdQuery);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}