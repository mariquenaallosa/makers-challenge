package com.challenge.loansystem.application.dto.LoanDto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateLoanRequest(
        @NotNull @DecimalMin("1000.00") BigDecimal amount,
        @NotNull @Min(6) @Max(60) Integer termMonths
) {}

