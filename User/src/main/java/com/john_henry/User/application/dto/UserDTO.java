package com.john_henry.User.application.dto;

import com.john_henry.User.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    @NotBlank(message = "Username is required")
    private String username;
    @NotNull(message = "password is required")
    private String pwd;
    @Email(message = "Invalid email format")
    private String email;

    private String numberPhone;

    @NotNull(message = "Role is required")
    private Role rol;

    private LocalDateTime registrationDate;
    private Boolean userActive;
}
