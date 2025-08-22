package com.recipemanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be 3 to 30 characters long")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Only letters, numbers and underscores")
    private String username;
    // only for registration, not returned in GET
    @NotBlank(message = "Password required")
    @Size(min = 6, max = 100, message = "Must be 6 to 100 characters long")
    private String password;

    @NotBlank(message = "Email required")
    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String email;

    private String role;
}
