package com.john_henry.User.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String pwd;
    private String email;
    private String numberPhone;
    private Role rol;
    private LocalDateTime registrationDate;
    private Boolean userActive;

}
