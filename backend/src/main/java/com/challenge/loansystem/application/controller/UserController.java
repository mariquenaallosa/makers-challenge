package com.challenge.loansystem.application.controller;

import com.challenge.loansystem.application.dto.AuthDto.LoginRequest;
import com.challenge.loansystem.application.dto.AuthDto.TokenResponse;
import com.challenge.loansystem.application.dto.UserDto.UserRequest;
import com.challenge.loansystem.application.dto.UserDto.UserResponse;
import com.challenge.loansystem.application.service.AuthService;
import com.challenge.loansystem.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
