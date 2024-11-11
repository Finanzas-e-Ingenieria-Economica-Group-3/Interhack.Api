package interhack.api.security.model.dto.response;

import interhack.api.users.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa la respuesta de la API cuando se registra un usuario
 * @author Jamutaq Ortega
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredCompanyResponseDto {
    private Long companyId;
    private String name;
    private String ruc;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
