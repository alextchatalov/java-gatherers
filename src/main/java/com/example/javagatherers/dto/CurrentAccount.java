package com.example.javagatherers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

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

    public void commitTransaction() {
        this.transactions.stream().gather(Gatherers.mapConcurrent(2, transaction -> {

            long threadId = Thread.currentThread().getId();
            System.out.println("Thread ID [" + threadId + "] Processing transaction: " +
                    transaction.getChannel() + " Value: " + transaction.getValue());

            BigDecimal value = transaction.getValue();
                    if (this.balance.compareTo(value) < 0) {
                        throw new IllegalArgumentException("Insufficient balance");
                    }
                    if (value.signum() > 0) {
                        this.balance = this.balance.add(value);
                    } else {
                        this.balance = this.balance.subtract(value.abs());
                    }
                    return transaction;
                })).count();
    }

}
