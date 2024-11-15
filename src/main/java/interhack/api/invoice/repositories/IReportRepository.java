package interhack.api.invoice.repositories;

import interhack.api.invoice.models.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE r.invoice.invoiceId = :invoiceId")
    Optional<Report> findByInvoiceId(Long invoiceId);

    @Override
    @Query("SELECT r FROM Report r " +
            "JOIN FETCH r.invoice i " +
            "JOIN FETCH r.bank b "
    )
    List<Report> findAll();

    @Override
    @Query("SELECT r FROM Report r " +
            "JOIN FETCH r.invoice i " +
            "JOIN FETCH r.bank b " +
            "WHERE r.reportId = :reportId"
    )
    Optional<Report> findById(Long reportId);

    @Query("SELECT r FROM Report r " +
            "JOIN FETCH r.invoice i " +
            "JOIN FETCH r.bank b " +
            "WHERE i.company.companyId = :companyId"
    )
    List<Report> findByInvoiceCompanyCompanyId(Long companyId);
}
