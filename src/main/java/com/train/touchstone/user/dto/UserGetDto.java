package com.train.touchstone.user.dto;

import com.train.touchstone.user.domain.AuthUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserGetDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserGetDto(AuthUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
