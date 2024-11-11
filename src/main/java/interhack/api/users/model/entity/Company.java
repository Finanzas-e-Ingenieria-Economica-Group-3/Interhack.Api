package interhack.api.users.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 11)
    private String ruc;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    /**
     * -Info: MUCHOS "usuarios" pueden tener MUCHOS "roles"
     * -JoinTable: la tabla intermediaria que se creará
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


}
