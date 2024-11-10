package interhack.api.users.service;

import interhack.api.shared.dto.enums.EStatus;
import interhack.api.shared.dto.response.ApiResponse;
import interhack.api.shared.exception.ResourceNotFoundException;
import interhack.api.users.model.dto.UserResponseDto;
import interhack.api.users.model.entity.User;
import interhack.api.users.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio para usuarios
 */
@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(IUserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<UserResponseDto> profile(Long userId) {
       var user = userRepository.findById(userId)
               .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id " + userId));

       //se mapea el usuario a un DTO
       var userDto = modelMapper.map(user, UserResponseDto.class);

       return new ApiResponse<>("Usuario encontrado", EStatus.SUCCESS, userDto);
    }

    @Override
    public ApiResponse<Object> deleteById(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id " + userId));

        //elimina al usuario
        userRepository.delete(user);

        return new ApiResponse<>("Usuario eliminado correctamente", EStatus.SUCCESS, null);
    }
    @Override
    public User findByUsername(String username) {
        // Implementación para buscar el usuario por su nombre de usuario
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
