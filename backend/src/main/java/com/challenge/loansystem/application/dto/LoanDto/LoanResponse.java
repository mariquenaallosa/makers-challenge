package com.challenge.loansystem.application.dto.LoanDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record LoanResponse(
        Long id,
        String status,
        BigDecimal amount,
        Integer termMonths,
        Instant createdAt,
        Instant updatedAt
) {}
