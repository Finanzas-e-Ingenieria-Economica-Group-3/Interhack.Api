package interhack.api.users.infrastructure.repositories;

import interhack.api.users.domain.model.entities.Role;
import interhack.api.users.domain.model.valueObjets.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);

    boolean existsByName(Roles name);
}
