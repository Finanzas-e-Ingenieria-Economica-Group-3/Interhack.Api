package interhack.api.security.service;

import interhack.api.security.model.dto.request.LoginRequestDto;
import interhack.api.security.model.dto.request.RegisterRequestDto;
import interhack.api.security.model.dto.response.RegisteredCompanyResponseDto;
import interhack.api.security.model.dto.response.TokenResponseDto;
import interhack.api.shared.dto.response.ApiResponse;

/**
 * Servicio para autenticación y registro de usuarios
 */
public interface IAuthService {
    /**
     * Registra un usuario
     * @param request Datos para el registro
     * @return Usuario registrado
     */
    ApiResponse<RegisteredCompanyResponseDto> registerCompany(RegisterRequestDto request);

    /**
     * Realiza el login del usuario
     * @param request Credenciales
     * @return Token de acceso
     */
    ApiResponse<TokenResponseDto> login(LoginRequestDto request);
}
