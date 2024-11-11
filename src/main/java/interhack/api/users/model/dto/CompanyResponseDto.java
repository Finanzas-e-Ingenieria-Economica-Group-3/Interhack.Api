package interhack.api.users.model.dto;

import interhack.api.users.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO para respuesta de datos de usuario
 * @author Jamutaq Ortega
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {
    private Long companyId;
    private String name;
    private String ruc;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
