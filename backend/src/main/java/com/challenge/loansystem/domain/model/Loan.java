package com.challenge.loansystem.domain.model;

import com.challenge.loansystem.domain.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal; import java.time.Instant;

@Entity @Table(name="loans")
@Getter @Setter
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable=false)
    private Integer termMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private LoanStatus status = LoanStatus.REQUESTED;

    @Column(nullable=false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    public boolean isFinalState() {
        return status == LoanStatus.APPROVED || status == LoanStatus.REJECTED;
    }
}
