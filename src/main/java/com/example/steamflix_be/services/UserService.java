package com.example.steamflix_be.services;

import com.example.steamflix_be.exceptions.AppException;
import com.example.steamflix_be.models.User;
import com.example.steamflix_be.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;

    public User getUserById(String id) {
        if (id == null || id.isBlank()) {
            throw new AppException("INVALID_ID", "User ID must not be empty", HttpStatus.BAD_REQUEST);
        }

        return userRepo.findById(id).orElseThrow(() -> new AppException(
                "NOT_FOUND",
                "User with ID " + id + " not found",
                HttpStatus.NOT_FOUND
        ));
    }
}
