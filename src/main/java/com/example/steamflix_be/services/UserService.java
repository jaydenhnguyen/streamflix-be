package com.example.steamflix_be.services;

import com.example.steamflix_be.models.User;
import com.example.steamflix_be.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;

    public User getUserById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("User ID must not be empty");
        }

        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("User with ID " + id + " not found");
        }
    }
}
