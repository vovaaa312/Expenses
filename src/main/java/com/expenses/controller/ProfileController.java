package com.expenses.controller;

import com.expenses.model.dTo.UserDto;
import com.expenses.model.user.User;
import com.expenses.service.AuthService;
import com.expenses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.PUT})
public class ProfileController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateUsername")
    public ResponseEntity<?> updateUsername(@AuthenticationPrincipal User principal,
                                            @RequestParam String newUsername) {
        User updatedUser = userService.updateUsername(principal.getId(), newUsername);
        return ResponseEntity.ok(User.toDto(updatedUser));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal User principal,
                                         @RequestParam String newEmail) {
        User updatedUser = userService.updateEmail(principal.getId(), newEmail);
        return ResponseEntity.ok(User.toDto(updatedUser));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal User principal,
                                            @RequestParam String newPassword) {
        User updatedUser = userService.updatePassword(principal.getId(), newPassword);
        return ResponseEntity.ok(User.toDto(updatedUser));
    }
}