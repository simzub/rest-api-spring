package com.example.demo.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PersonRequest(
        @Size(min = 3, max = 15, message = "First name should be between 3 and 15 characters")
        @NotBlank
        String firstName,
        String lastName,
        @Email(message = "Invalid email format")
        String email,
        @Pattern(regexp = "^\\+?[0-9\\-\\s()]*$", message = "Invalid phone number format")
        String phone
) {
}