package com.example.steamflix_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String _id;
    private String email;
    private String firstName;
    private String lastName;
}
