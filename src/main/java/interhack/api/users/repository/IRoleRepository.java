package interhack.api.users.repository;

import interhack.api.users.model.entity.Role;
import interhack.api.users.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de roles
 * @author Jamutaq Ortega
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {
    /**
     * Busca un rol por su nombre
     * @param name Nombre del rol
     * @return Rol encontrado (si existe)
     */
    Optional<Role> findByName(ERole name);

    /**
     * Verifica si existe un rol por su nombre
     * @param name Nombre del rol
     * @return true si existe, false si no
     */
    boolean existsByName(ERole name);
}
