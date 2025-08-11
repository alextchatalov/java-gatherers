package com.example.javagatherers;

import com.example.javagatherers.dto.CurrentAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Main class for the Java Gatherers application.
 */
public class GathererFold {
    /**
     * The entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        //fold example
        CurrentAccount currentAccount = generateData(10);
        BigDecimal totalValue = currentAccount.getTransactions().stream()
                .gather(Gatherers.fold(
                        () -> BigDecimal.ZERO,
                        (accumulator, transaction) -> accumulator.add(transaction.getValue())
                ))
                .findFirst()
                .orElse(BigDecimal.ZERO);

        System.out.println("Total transaction value: " + totalValue);
    }

    private static CurrentAccount generateData(int numberOfTransactions) {
        CurrentAccount.CurrentAccountBuilder accountBuilder = CurrentAccount.builder().name("Picpay").balance(new BigDecimal("5489.47"));
        List<CurrentAccount.Transaction> transactions = new ArrayList<>();
        for (int i = 0; i <= numberOfTransactions; i++) {
            transactions.add(CurrentAccount.Transaction.builder()
                    .value(new BigDecimal(i * 2))
                    .channel(CurrentAccount.Channel.PIX)
                    .build());
        }
        return accountBuilder.transactions(transactions).build();
    }
}