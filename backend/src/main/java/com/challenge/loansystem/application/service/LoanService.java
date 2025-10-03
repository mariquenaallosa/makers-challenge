// src/main/java/com/challenge/loansystem/application/LoanService.java
package com.challenge.loansystem.application.service;

import com.challenge.loansystem.domain.enums.LoanStatus;
import com.challenge.loansystem.domain.model.Loan;
import com.challenge.loansystem.infraestructure.repository.LoanRepository;
import com.challenge.loansystem.application.dto.LoanDto.CreateLoanRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Cacheable(value = "loansByUser", key = "#userId")
    public List<Loan> findByUser(Long userId) {
        return loanRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Cacheable(value = "loansByStatus", key = "#status")
    public List<Loan> findByStatus(LoanStatus status) {
        return loanRepository.findByStatusOrderByCreatedAtAsc(status);
    }

    @Transactional
    @CacheEvict(value = "loansByUser", key = "#userId")
    public Loan create(Long userId, CreateLoanRequest req) {
        Loan loan = new Loan();
        loan.setUserId(userId);
        loan.setAmount(req.amount());
        loan.setTermMonths(req.termMonths());
        loan.setStatus(LoanStatus.REQUESTED);
        return loanRepository.save(loan);
    }

    @Transactional
    @CacheEvict(value = {"loansByUser", "loansByStatus"}, allEntries = true)
    public Loan approve(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow();
        loan.setStatus(LoanStatus.APPROVED);
        return loanRepository.save(loan);
    }

    @Transactional
    @CacheEvict(value = {"loansByUser", "loansByStatus"}, allEntries = true)
    public Loan reject(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow();
        loan.setStatus(LoanStatus.REJECTED);
        return loanRepository.save(loan);
    }
}
