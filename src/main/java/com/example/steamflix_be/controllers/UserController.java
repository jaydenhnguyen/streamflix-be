package com.example.steamflix_be.controllers;

import com.example.steamflix_be.annotations.SecureRoute;
import com.example.steamflix_be.models.User;
import com.example.steamflix_be.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/me")
    @SecureRoute
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
