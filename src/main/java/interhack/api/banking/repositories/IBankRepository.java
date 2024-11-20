package interhack.api.banking.repositories;

import interhack.api.banking.models.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBankRepository extends JpaRepository<Bank, Long> {

}
