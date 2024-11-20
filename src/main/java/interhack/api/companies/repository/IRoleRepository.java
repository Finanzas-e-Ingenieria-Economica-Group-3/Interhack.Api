package interhack.api.companies.repository;

import interhack.api.companies.model.entity.Role;
import interhack.api.companies.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de roles
 * @author Jamutaq Ortega
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    boolean existsByName(ERole name);
}
