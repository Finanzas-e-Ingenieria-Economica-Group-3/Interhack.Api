package interhack.api.invoice.models.entities;

import interhack.api.clients.models.entities.Client;
import interhack.api.companies.model.entity.Company;
import interhack.api.invoice.models.enums.ECurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;


    public Invoice(Client client, Company company, Long amount, ECurrencyType currencyType, LocalDate issueDate, LocalDate dueDate) {
        this.client = client;
        this.company = company;
        this.amount = amount;
        this.currencyType = currencyType;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public String getFormattedInvoiceId() {
        return String.format("%04d", invoiceId);
    }
}
