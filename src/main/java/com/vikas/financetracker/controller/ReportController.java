package com.vikas.financetracker.controller;

import com.example.reactivefinancetracker.dto.CategorySpending;
import com.example.reactivefinancetracker.dto.MonthlySpendingSummary;
import com.example.reactivefinancetracker.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly-summary")
    public Flux<MonthlySpendingSummary> getMonthlySummary(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return reportService.getMonthlySummary(username);
    }

    @GetMapping("/category-summary")
    public Flux<CategorySpending> getCategorySummary(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return reportService.getCategoryWiseSummary(username);
    }
}

