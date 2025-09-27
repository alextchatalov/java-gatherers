package com.example.javagatherers.dto;

import java.util.Map;

public record TransactionStats(
        double totalAmount,
        double totalWithoutEntertainment,
        Map<String, Double> categoryTotals,
        int transactionCount
) {}
