package interhack.api.security.controller;

import interhack.api.security.model.dto.request.LoginRequestDto;
import interhack.api.security.model.dto.request.RegisterRequestDto;
import interhack.api.security.model.dto.response.RegisteredCompanyResponseDto;
import interhack.api.security.model.dto.response.TokenResponseDto;
import interhack.api.security.service.AuthService;
import interhack.api.security.service.IAuthService;
import interhack.api.shared.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para autenticación y registro de usuarios
 */
@Tag(name = "Auth")
@SecurityRequirements //desactiva la seguridad para este controlador (swagger)
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final IAuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Inicia sesión")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        var res = service.login(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @Operation(summary = "Registra una nueva empresa")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisteredCompanyResponseDto>> registerCompany(@Valid @RequestBody RegisterRequestDto request) {
        var res = service.registerCompany(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
