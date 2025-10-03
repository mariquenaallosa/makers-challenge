package com.challenge.loansystem.application.controller;

import com.challenge.loansystem.application.service.LoanService;
import com.challenge.loansystem.application.dto.LoanDto.CreateLoanRequest;
import com.challenge.loansystem.application.dto.LoanDto.LoanResponse;
import com.challenge.loansystem.domain.enums.LoanStatus;
import com.challenge.loansystem.infraestructure.repository.UserRepository;
import com.challenge.loansystem.infraestructure.security.CurrentUser;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;

    public LoanController(LoanService loanService, CurrentUser currentUser, UserRepository userRepository) {
        this.loanService = loanService;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LoanResponse> create(@Valid @RequestBody CreateLoanRequest req){
        var userId = userRepository.findByEmail(currentUser.email()).orElseThrow().getId();
        var loan = loanService.create(userId, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LoanResponse(loan.getId(), loan.getStatus().name(), loan.getAmount(), loan.getTermMonths(), loan.getCreatedAt(), loan.getUpdatedAt()));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public List<LoanResponse> myLoans(){
        var userId = userRepository.findByEmail(currentUser.email()).orElseThrow().getId();
        return loanService.findByUser(userId).stream()
                .map(l -> new LoanResponse(l.getId(), l.getStatus().name(), l.getAmount(), l.getTermMonths(), l.getCreatedAt(), l.getUpdatedAt()))
                .toList();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<LoanResponse> byStatus(@RequestParam(required=false) LoanStatus status){
        var list = (status==null) ? loanService.findByStatus(LoanStatus.REQUESTED) : loanService.findByStatus(status);
        return list.stream().map(l -> new LoanResponse(l.getId(), l.getStatus().name(), l.getAmount(), l.getTermMonths(), l.getCreatedAt(), l.getUpdatedAt())).toList();
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public LoanResponse approve(@PathVariable Long id){
        var l = loanService.approve(id);
        return new LoanResponse(l.getId(), l.getStatus().name(), l.getAmount(), l.getTermMonths(), l.getCreatedAt(), l.getUpdatedAt());
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public LoanResponse reject(@PathVariable Long id){
        var l = loanService.reject(id);
        return new LoanResponse(l.getId(), l.getStatus().name(), l.getAmount(), l.getTermMonths(), l.getCreatedAt(), l.getUpdatedAt());
    }
}
