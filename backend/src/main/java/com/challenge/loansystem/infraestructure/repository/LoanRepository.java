package com.challenge.loansystem.infraestructure.repository;

import com.challenge.loansystem.domain.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.loansystem.domain.enums.LoanStatus;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Cacheable(value = "loansByUser", key = "#userId")
    List<Loan> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Loan> findByStatusOrderByCreatedAtAsc(LoanStatus status);
}
