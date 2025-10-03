package com.challenge.loansystem.application.service;

import com.challenge.loansystem.application.dto.AuthDto.LoginRequest;
import com.challenge.loansystem.application.dto.AuthDto.TokenResponse;
import com.challenge.loansystem.infraestructure.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthService(AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil) {
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public TokenResponse login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String email = request.email();
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        String token = jwtTokenUtil.generateToken(email, roles);
        return new TokenResponse(token, roles);
    }
}
