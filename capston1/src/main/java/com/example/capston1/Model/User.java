package com.example.capston1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 6, message = "Username length must more then 5")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "Password must be 6 length, and contain letters and numbers only")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email format must be right")
    private String email;

    @NotEmpty(message = "Role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be Admin or Customer only")
    private String role;

    @NotNull(message = "Balance cannot be empty")
    @Positive(message = "Balance must be a positive number")
    private double balance;
}
