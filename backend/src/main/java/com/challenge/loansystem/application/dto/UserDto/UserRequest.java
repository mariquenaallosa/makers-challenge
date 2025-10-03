package com.challenge.loansystem.application.dto.UserDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest {
    private String email;
    private String password;
}
