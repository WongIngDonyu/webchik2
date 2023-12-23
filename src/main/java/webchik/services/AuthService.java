package webchik.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webchik.models.User;
import webchik.models.UserRole;
import webchik.repositories.UserRepository;
import webchik.repositories.UserRoleRepository;
import webchik.services.dtos.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }
    public void register(UserRegistrationDto registrationDTO) {
        var userRole = userRoleRepository.
                findByRole(UserRole.Role.USER).orElseThrow();
        User user = new User(
                registrationDTO.getUsername(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getFirstName(),
                registrationDTO.getLastName(),
                true, registrationDTO.getImageUrl(), null, null
        );

        user.setUserRoles(List.of(userRole));

        this.userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}
