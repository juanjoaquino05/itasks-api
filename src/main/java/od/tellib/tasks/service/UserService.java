package od.tellib.tasks.service;

import od.tellib.tasks.converter.UserConverter;
import od.tellib.tasks.dto.request.ModifyUserRequest;
import od.tellib.tasks.dto.request.SignupRequest;
import od.tellib.tasks.exception.ResourceNotFoundException;
import od.tellib.tasks.exception.UserEmailExistsException;
import od.tellib.tasks.exception.UsernameTakenException;
import od.tellib.tasks.model.ERole;
import od.tellib.tasks.model.Role;
import od.tellib.tasks.model.User;
import od.tellib.tasks.repository.RoleRepository;
import od.tellib.tasks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) throw new ResourceNotFoundException("User");

        return user.get();
    }
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()) throw new ResourceNotFoundException("User");

        return user.get();
    }

    public boolean existsByUserName(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User createUser(SignupRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getPassword());

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = getRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = getRole(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "moderator":
                        Role modRole = getRole(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = getRole(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public void validateIfUserExists(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameTakenException();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserEmailExistsException();
        }
    }

    public Optional<Role> getRole(ERole eRole) {
        return roleRepository.findByName(eRole.name());
    }

    public User registerUser(SignupRequest signUpRequest) {
        validateIfUserExists(signUpRequest);

        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Create new user's account
        return createUser(signUpRequest);
    }

    public ResponseEntity modifyUser(ModifyUserRequest request) {
        var user = userRepository.findByUsername(request.getUsername());
        if(!user.isPresent()) throw new ResourceNotFoundException("User");

        User toUpdate = user.get();

        request.setPassword(encoder.encode(request.getPassword()));
        UserConverter.updateUserData(toUpdate, request);

        toUpdate = userRepository.save(toUpdate);

        return ResponseEntity.ok()
                .body(toUpdate);
    }

    public User deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) throw new ResourceNotFoundException("User");

        userRepository.deleteById(id);

        return user.get();
    }
}
