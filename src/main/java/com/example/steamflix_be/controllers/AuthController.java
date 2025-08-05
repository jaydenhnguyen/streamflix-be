package com.example.steamflix_be.controllers;

import com.example.steamflix_be.dto.LoginRequest;
import com.example.steamflix_be.dto.LoginResponse;
import com.example.steamflix_be.dto.RegisterRequest;
import com.example.steamflix_be.dto.RegisterResponse;
import com.example.steamflix_be.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse savedUser = authService.register(registerRequest);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}