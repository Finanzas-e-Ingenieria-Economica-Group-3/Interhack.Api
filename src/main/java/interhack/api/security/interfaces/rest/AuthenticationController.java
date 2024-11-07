package interhack.api.security.interfaces.rest;

import interhack.api.security.domain.model.commands.SignUpCommand;
import interhack.api.security.domain.services.AuthCommandService;
import interhack.api.security.interfaces.rest.resources.AuthenticatedUserResource;
import interhack.api.security.interfaces.rest.resources.SignInResource;
import interhack.api.security.interfaces.rest.resources.SignUpResource;
import interhack.api.security.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import interhack.api.security.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import interhack.api.security.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import interhack.api.users.domain.model.aggregates.User;
import interhack.api.users.interfaces.rest.resources.UserResource;
import interhack.api.users.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Management Endpoints")
public class AuthenticationController {
    private final AuthCommandService userCommandService;

    public AuthenticationController(AuthCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.loginUser(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
                authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        SignUpCommand signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        Optional<User> user = userCommandService.registerUser(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        UserResource userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
