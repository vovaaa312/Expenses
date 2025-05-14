package com.expenses.service;

import com.expenses.exception.UserNotFoundException;
import com.expenses.exception.UserWithEmailExistsException;
import com.expenses.exception.UserWithUsernameExistsException;
import com.expenses.model.request.AuthRequest;
import com.expenses.model.request.RegisterRequest;
import com.expenses.model.response.AuthResponse;
import com.expenses.model.user.SystemRole;
import com.expenses.model.user.User;
import com.expenses.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            String errorMessage = "User with username: {" + request.getUsername() + "} already exists";
            throw new UserWithUsernameExistsException(errorMessage);
        }
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            String errorMessage = "User with email: {" + request.getEmail() + "} already exists";
            throw new UserWithEmailExistsException(errorMessage);

        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(SystemRole.SYSTEM_USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .jwtResponse(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .jwtResponse(jwtToken)
                .build();
    }




}