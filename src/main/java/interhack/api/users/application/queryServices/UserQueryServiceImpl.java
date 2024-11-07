package interhack.api.users.application.queryServices;

import interhack.api.users.domain.model.aggregates.User;
import interhack.api.users.domain.model.queries.GetAllUsersQuery;
import interhack.api.users.domain.model.queries.GetUserByIdQuery;
import interhack.api.users.domain.services.UserQueryService;
import interhack.api.users.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUser(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> userId(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }
}
