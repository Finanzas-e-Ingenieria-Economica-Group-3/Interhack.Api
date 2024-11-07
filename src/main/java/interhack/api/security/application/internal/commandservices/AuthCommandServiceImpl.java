package interhack.api.security.application.internal.commandservices;

import interhack.api.security.application.internal.outboundservices.hashing.HashingService;
import interhack.api.security.application.internal.outboundservices.tokens.TokenService;
import interhack.api.security.domain.model.commands.SignInCommand;
import interhack.api.security.domain.model.commands.SignUpCommand;
import interhack.api.security.domain.services.AuthCommandService;
import interhack.api.users.domain.model.aggregates.User;
import interhack.api.users.infrastructure.repositories.RoleRepository;
import interhack.api.users.infrastructure.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthCommandServiceImpl implements AuthCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public AuthCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> registerUser(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())){
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(command.email())){
            throw new RuntimeException("Email already exists");
        }
        var roles = command.roles().stream().map(role ->
                roleRepository.findByName(role.getName())
                .orElseThrow(() -> new RuntimeException("Role name not found"))).toList();

        var user = new User(command.username(), hashingService.encode(command.password()), command.email(), roles);
        userRepository.save(user);
        return userRepository.findByEmail(command.email());
    }

    @Override
    public Optional<ImmutablePair<User, String>> loginUser(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty()) throw new RuntimeException("email or password invalid");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("email or password invalid");
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }
}
