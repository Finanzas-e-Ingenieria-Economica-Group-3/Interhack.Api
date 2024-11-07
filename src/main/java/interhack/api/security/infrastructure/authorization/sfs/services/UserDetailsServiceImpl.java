package interhack.api.security.infrastructure.authorization.sfs.services;

import interhack.api.security.infrastructure.authorization.sfs.model.UserDetailsImpl;
import interhack.api.users.infrastructure.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa la interfaz UserDetailsService de Spring Security para obtener los datos del usuario autenticado
 */
@Service("defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene los datos del usuario autenticado por su username
     * @param username Nombre de usuario o email del usuario autenticado
     * @return Datos del usuario autenticado
     * @throws UsernameNotFoundException Excepción de usuario no encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}