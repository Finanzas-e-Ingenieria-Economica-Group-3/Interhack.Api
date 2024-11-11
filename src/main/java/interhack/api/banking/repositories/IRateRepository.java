package interhack.api.banking.repositories;

import interhack.api.banking.models.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRateRepository extends JpaRepository<Rate, Long> {
}
