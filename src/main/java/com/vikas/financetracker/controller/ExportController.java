package com.vikas.financetracker.controller;

import com.example.reactivefinancetracker.domain.Transaction;
import com.example.reactivefinancetracker.dto.BudgetResponse;
import com.example.reactivefinancetracker.repository.TransactionRepository;
import com.example.reactivefinancetracker.service.BudgetService;
import com.example.reactivefinancetracker.util.CsvExportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/v1/export")
@RequiredArgsConstructor
public class ExportController {

    private final TransactionRepository transactionRepo;
    private final BudgetService budgetService;

    @GetMapping("/transactions")
    public Flux<ResponseEntity<ByteArrayResource>> exportTransactions(
            @RequestParam String from,
            @RequestParam String to,
            Authentication auth) {

        String userId = ((UserDetails) auth.getPrincipal()).getUsername();
        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);

        return transactionRepo.findAllByUserId(userId)
                .filter(tx -> {
                    LocalDateTime txTime = LocalDateTime.ofInstant(tx.getTimestamp(), ZoneOffset.UTC);
                    return !txTime.isBefore(fromDate) && !txTime.isAfter(toDate);
                })
                .collectList()
                .map(list -> {
                    try {
                        byte[] csv = CsvExportUtil.exportTransactions(list);
                        return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType("text/csv"))
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv")
                                .body(new ByteArrayResource(csv));
                    } catch (Exception e) {
                        throw new RuntimeException("CSV export failed", e);
                    }
                });
    }

    @GetMapping("/budget")
    public Flux<ResponseEntity<ByteArrayResource>> exportBudget(
            @RequestParam int year,
            @RequestParam int month,
            Authentication auth) {

        String userId = ((UserDetails) auth.getPrincipal()).getUsername();

        return budgetService.getBudgetStatus(userId, year, month)
                .collectList()
                .map(list -> {
                    try {
                        byte[] csv = CsvExportUtil.exportBudgetSummary(list);
                        return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType("text/csv"))
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=budget-summary.csv")
                                .body(new ByteArrayResource(csv));
                    } catch (Exception e) {
                        throw new RuntimeException("CSV export failed", e);
                    }
                });
    }
}

