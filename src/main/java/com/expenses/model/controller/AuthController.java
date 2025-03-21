package com.expenses.model.controller;

import com.expenses.model.request.AuthRequest;
import com.expenses.model.request.RegisterRequest;
import com.expenses.model.response.AuthResponse;
import com.expenses.repository.UserRepository;
import com.expenses.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)  {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            String errorMessage = "User with username: {" + request.getUsername() + "} already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        if(userRepository.findUserByEmail(request.getEmail()).isPresent()){
            String errorMessage = "User with email: {" + request.getEmail() + "} already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }




}