package interhack.api.users.domain.services;

import interhack.api.users.domain.model.aggregates.User;
import interhack.api.users.domain.model.queries.GetAllUsersQuery;
import interhack.api.users.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> listUser(GetAllUsersQuery query);
    Optional<User> userId(GetUserByIdQuery query);
}
