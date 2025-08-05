package com.example.steamflix_be.services;

import com.example.steamflix_be.dto.LoginRequest;
import com.example.steamflix_be.dto.LoginResponse;
import com.example.steamflix_be.dto.RegisterResponse;
import com.example.steamflix_be.exceptions.AppException;
import org.springframework.http.HttpStatus;
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

    public RegisterResponse register(User user) {
        if (emailExists(user.getEmail())) {
            throw new AppException(
                    "DUPLICATE",
                    "User with this email already exists.",
                    HttpStatus.CONFLICT
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AppException(
                        "INVALID_CREDENTIALS",
                        "Invalid email or password.",
                        HttpStatus.UNAUTHORIZED
                ));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppException(
                    "INVALID_CREDENTIALS",
                    "Invalid email or password.",
                    HttpStatus.UNAUTHORIZED
            );
        }

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponse(
                accessToken,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}