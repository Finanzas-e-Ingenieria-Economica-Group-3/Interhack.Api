package interhack.api.companies.repository;

import interhack.api.companies.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ICompanyRepository extends JpaRepository<Company, Long> {
    /**
     * Busca una compañia por su email o username
     * @param email Email
     * @return Usuario encontrado
     */
    Optional<Company> findByEmail(String email);

    Optional<Company> findByName(String name);

    /**
     * Verifica si existe un usuario por su email
     * @param email Email
     * @return True si existe, false si no
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario por su username
     * @param name Username
     * @return True si existe, false si no
     */
    boolean existsByName(String name);
}
