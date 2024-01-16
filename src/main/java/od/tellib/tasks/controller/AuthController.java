package od.tellib.tasks.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import od.tellib.tasks.dto.request.LoginRequest;
import od.tellib.tasks.dto.request.SignupRequest;
import od.tellib.tasks.dto.response.JwtResponse;
import od.tellib.tasks.dto.response.MessageResponse;
import od.tellib.tasks.security.jwt.JwtUtils;
import od.tellib.tasks.security.services.UserDetailsImpl;
import od.tellib.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final UserService userService;
    final PasswordEncoder encoder;
    final JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder encoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        userService.validateIfUserExists(signUpRequest);

        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Create new user's account
        userService.createUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}