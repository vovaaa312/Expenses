package com.expenses.model.controller;

import com.expenses.service.JwtService;
import io.jsonwebtoken.Jwt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt-test")
@RequiredArgsConstructor
public class JwtTestController {
    private final JwtService jwtService;

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        try{
            return ResponseEntity.ok(jwtService.getTokenDetails(token));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
