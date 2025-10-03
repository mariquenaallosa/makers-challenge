package com.challenge.loansystem.application.dto.AuthDto;

import java.util.List;

public record TokenResponse(String token, List<String> roles) {}

