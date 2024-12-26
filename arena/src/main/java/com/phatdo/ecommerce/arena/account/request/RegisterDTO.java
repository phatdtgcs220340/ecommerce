package com.phatdo.ecommerce.arena.account.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @Email(message = "Invalid email")
        String email,
        @Size(min = 8, message = "Password must be at least 8 length")
        String password,
        @NotEmpty(message = "Invalid field")
        String fullName,
        @NotEmpty(message = "Invalid field")
        String telephone,
        @NotEmpty(message = "Invalid field")
        String country) {
}
