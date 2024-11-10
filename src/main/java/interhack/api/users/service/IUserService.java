package interhack.api.users.service;

import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.users.model.dto.UserResponseDto;
import interhack.api.users.model.entity.User;

/**
 * Servicio para operaciones con usuarios
 * @author Jamutaq Ortega
 */
public interface IUserService {
    /**
     * Obtiene los datos de un usuario por su id
     * @param userId id del usuario
     * @return Datos del usuario
     */
    ApiResponse<UserResponseDto> profile(Long userId);

    /**
     * Elimina un usuario por su id
     * @param userId id del usuario
     * @return Respuesta de la operación
     */
    ApiResponse<Object> deleteById(Long userId);

    User findByUsername(String username);
}
