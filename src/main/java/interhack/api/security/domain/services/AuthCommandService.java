package interhack.api.security.domain.services;

import interhack.api.security.domain.model.commands.SignInCommand;
import interhack.api.security.domain.model.commands.SignUpCommand;
import interhack.api.users.domain.model.aggregates.User;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface AuthCommandService {
    Optional<User> registerUser(SignUpCommand command);
    Optional<ImmutablePair<User, String>> loginUser(SignInCommand command);
}
