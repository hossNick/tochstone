package com.train.touchstone.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostDto {

    private String firstName;
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    private String password;
}
