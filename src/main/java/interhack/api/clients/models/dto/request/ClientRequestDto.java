package interhack.api.clients.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {
    @NotEmpty(message = "The client name is required")
    private String name;

    @NotEmpty(message = "The client lastname is required")
    private String lastname;

    @NotEmpty(message = "The client email is required")
    @Email(message = "The email format is not valid")
    private String email;

    @NotEmpty(message = "The client DNI is required")
    @Size(min = 8, max = 8, message = "The DNI must have 8 characters")
    private String dni;
}
