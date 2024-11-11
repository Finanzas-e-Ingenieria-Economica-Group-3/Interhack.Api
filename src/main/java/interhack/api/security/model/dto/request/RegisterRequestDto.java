package interhack.api.security.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa el request body para el registro de un usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @NotEmpty(message = "El nombre es requerido")
    private String name;

    @NotEmpty(message = "El Ruc es requerido")
    @Size(min = 11, max = 11, message = "El ruc debe tener 11 caracteres")
    private String ruc;


    @NotEmpty(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotEmpty(message = "La contraseña es requerida")
    @Size(min = 3, message = "La contraseña debe tener por lo menos 3 caracteres")
    private String password;

}
