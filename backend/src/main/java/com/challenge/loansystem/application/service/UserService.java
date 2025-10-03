package com.challenge.loansystem.application.service;

import com.challenge.loansystem.application.dto.UserDto.UserRequest;
import com.challenge.loansystem.application.dto.UserDto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserResponse register(UserRequest request) {
        // Aquí iría la lógica real de registro (guardar usuario, validaciones, etc.)
        return new UserResponse(request.getEmail());
    }
}
