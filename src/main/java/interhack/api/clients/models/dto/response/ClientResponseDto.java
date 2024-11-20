package interhack.api.clients.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    private Long clientId;
    private String name;
    private String lastname;
    private String email;
    private String dni;
    private Long companyId;
}
