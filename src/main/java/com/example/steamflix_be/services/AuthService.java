package com.example.steamflix_be.services;

import com.example.steamflix_be.dto.LoginRequest;
import org.springframework.stereotype.Service;
import com.example.steamflix_be.models.User;
import com.example.steamflix_be.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    public boolean emailExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    public User register(User user) {
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return jwtService.generateAccessToken(user);
    }
}