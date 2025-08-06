package com.example.javagatherers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount {

    private String name;
    private BigDecimal balance;
    private List<Transaction> transactions;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction {
        private Channel channel;
        private BigDecimal value;
        
    }
    
    public enum Channel{
        PIX,
        P2P
    }

}
