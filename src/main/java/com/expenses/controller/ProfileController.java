package com.expenses.controller;

import com.expenses.model.dTo.UserDto;
import com.expenses.model.user.User;
import com.expenses.service.AuthService;
import com.expenses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.PUT})
public class ProfileController {

    private final AuthService authService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateUsername")
    public ResponseEntity<?> updateUsername(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam String newUsername) {
        User authenticatedUser = authService.getAuthenticatedUser(authHeader);
        User updatedUser = userService.updateUsername(authenticatedUser.getId(), newUsername);
        return ResponseEntity.ok(User.toDto(updatedUser));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestHeader("Authorization") String authHeader,
                                          @RequestParam String newEmail) {
        User authenticatedUser = authService.getAuthenticatedUser(authHeader);
        User updatedUser = userService.updateEmail(authenticatedUser.getId(), newEmail);
        return ResponseEntity.ok(User.toDto(updatedUser));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam String newPassword) {
        User authenticatedUser = authService.getAuthenticatedUser(authHeader);
        User updatedUser = userService.updatePassword(authenticatedUser.getId(), newPassword);
        return ResponseEntity.ok(User.toDto(updatedUser));
    }
}