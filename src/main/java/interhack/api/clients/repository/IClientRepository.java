package interhack.api.clients.repository;

import interhack.api.clients.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Long> {
    /***
    * Verifica si existe un cliente por su email
    * @param email Email
    * @return True si existe, False si no
    ***/
    boolean existsByEmail(String email);

    /***
    * Verifica si existe un cliente por su DNI
    * @param dni DNI
    * @return True si existe, False si no
    ***/
    boolean existsByDni(String dni);

    @Query("SELECT c FROM Client c WHERE c.company.companyId = :companyId")
    List<Client> findByCompanyId(Long companyId);
}
