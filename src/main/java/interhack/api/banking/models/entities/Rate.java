package interhack.api.banking.models.entities;


import interhack.api.banking.models.enums.EPeriod;
import interhack.api.banking.models.enums.ERateType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de tasa a pagar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long rateId;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ERateType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EPeriod period;

    @OneToOne(mappedBy = "rate")
    private Bank bank;

    public Rate(double value, ERateType rateType, EPeriod period) {
        this.value = value;
        this.type = rateType;
        this.period = period;
    }
}
