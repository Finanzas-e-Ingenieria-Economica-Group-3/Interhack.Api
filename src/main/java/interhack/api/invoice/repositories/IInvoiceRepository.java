package interhack.api.invoice.repositories;

import interhack.api.invoice.models.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByCompanyCompanyId(Long companyId);
}
