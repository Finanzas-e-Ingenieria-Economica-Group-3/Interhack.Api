package interhack.api.clients.models.entities;

import interhack.api.clients.models.dto.request.ClientRequestDto;
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
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    public Client(ClientRequestDto request) {
        this.name = request.getName();
        this.lastname = request.getLastname();
        this.email = request.getEmail();
        this.dni = request.getDni();
    }

    public void update(ClientRequestDto request) {
        this.name = request.getName();
        this.lastname = request.getLastname();
        this.email = request.getEmail();
        this.dni = request.getDni();
    }
}
