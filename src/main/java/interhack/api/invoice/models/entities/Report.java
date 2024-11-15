package interhack.api.invoice.models.entities;

import interhack.api.banking.models.entities.Bank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(nullable = false)
    private double discount;

    @Column(nullable = false)
    private double netAmount;

    @Column(nullable = false)
    private double tcea;

    @OneToOne(mappedBy = "report")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    public Report(double discount, double netAmount, double tcea, Invoice invoice, Bank bank) {
        this.discount = discount;
        this.netAmount = netAmount;
        this.tcea = tcea;
        this.invoice = invoice;
        this.bank = bank;
    }

    public void UpdateReport(double discount, double netAmount, double tcea, Invoice invoice, Bank bank) {
        this.discount = discount;
        this.netAmount = netAmount;
        this.tcea = tcea;
        this.invoice = invoice;
        this.bank = bank;
    }
}
