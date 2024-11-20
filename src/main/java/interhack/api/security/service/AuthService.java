package interhack.api.security.service;

import interhack.api.security.jwt.provider.JwtTokenProvider;
import interhack.api.security.model.dto.request.LoginRequestDto;
import interhack.api.security.model.dto.request.RegisterRequestDto;
import interhack.api.security.model.dto.response.RegisteredCompanyResponseDto;
import interhack.api.security.model.dto.response.TokenResponseDto;
import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.CustomException;
import interhack.api.companies.model.entity.Company;
import interhack.api.companies.model.enums.ERole;
import interhack.api.companies.repository.IRoleRepository;
import interhack.api.companies.repository.ICompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Servicio para autenticación y registro de usuarios
 */
@Service
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final ICompanyRepository companyRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public AuthService(AuthenticationManager authenticationManager, ICompanyRepository companyRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<RegisteredCompanyResponseDto> registerCompany(RegisterRequestDto request) {
        //si el email ya está registrado
        if (companyRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(HttpStatus.CONFLICT, "El email '" + request.getEmail() + "' ya está registrado");
        }

        //si el username ya está registrado
        if (companyRepository.existsByName(request.getName())) {
            throw new CustomException(HttpStatus.CONFLICT, "El username '" + request.getName() + "' ya está registrado");
        }

        //si no existe, lo registra
        var user = Company.builder()
                .name(request.getName())
                .ruc(request.getRuc())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        //establecer los roles
        var roles = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo registrar el usuario, no se encontró el rol USER"));
        user.setRoles(Collections.singleton(roles)); //establece un solo rol

        //guarda el usuario
        var newUser = companyRepository.save(user);

        //mapea de la entidad al dto
        var responseData = modelMapper.map(newUser, RegisteredCompanyResponseDto.class);

        return new ApiResponse<>("Registro correcto", EStatus.SUCCESS, responseData);
    }

    @Override
    public ApiResponse<TokenResponseDto> login(LoginRequestDto request) {
        //se validan las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //establece la seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //se obtiene el token
        String token = jwtTokenProvider.generateToken(authentication);

        var responseData = new TokenResponseDto(token);
        return new ApiResponse<>("Autenticación correcta", EStatus.SUCCESS, responseData);
    }
}
