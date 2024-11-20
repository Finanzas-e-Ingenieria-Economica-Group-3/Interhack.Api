package interhack.api.banking.models.entities;

import interhack.api.banking.models.dtos.requests.BankRequest;
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
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long bankId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, length = 11)
    private String ruc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rate_id", referencedColumnName = "rate_id")
    private Rate rate;

    public Bank(String name, String imageUrl, String ruc, Rate rate) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.ruc = ruc;
        this.rate = rate;
    }

    public Bank(BankRequest request) {
        this.name = request.getName();
        this.ruc = request.getRuc();
        this.rate = new Rate(request.getRate());
    }
}
