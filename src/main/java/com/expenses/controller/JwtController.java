package com.expenses.controller;

import com.expenses.model.user.User;
import com.expenses.service.JwtService;
import com.expenses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class JwtController {

    private final JwtService jwtService;
    private final UserService userService;


    //    @GetMapping("/extractUsername")
//    public ResponseEntity<?> extractUsername(
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing Authorization header");
//        }
//        String token = authHeader.substring(7); // Remove "Bearer " prefix
//        String username = jwtService.extractUsername(token);
//        return ResponseEntity.ok(username);
//    }
    @GetMapping("/extractUsername")
    public ResponseEntity<?> extractUsername(
            @AuthenticationPrincipal User principal
    ) {

        return ResponseEntity.ok(principal.getUsername());
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<?> findByUsername(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing Authorization header");
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok(User.toDto(userService.findUserByUsername(username)));
    }
}
