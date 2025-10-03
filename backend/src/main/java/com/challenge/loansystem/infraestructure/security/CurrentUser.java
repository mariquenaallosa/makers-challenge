package com.challenge.loansystem.infraestructure.security;

import org.springframework.stereotype.Component;

@Component
public interface CurrentUser {
    String email();
}

