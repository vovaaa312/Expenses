package com.expenses.controller;

import com.expenses.model.dTo.mapper.UserMapper;
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

    @GetMapping("/extractUsername")
    public ResponseEntity<?> extractUsername(
            @AuthenticationPrincipal User principal
    ) {

        return ResponseEntity.ok(principal.getUsername());
    }


    @GetMapping("/extractAuthenticated")
    public ResponseEntity<?> extractAuthenticated(
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(UserMapper.toDto(principal));
    }

}
