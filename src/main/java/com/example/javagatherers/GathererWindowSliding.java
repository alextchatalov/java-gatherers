package com.example.javagatherers;

import com.example.javagatherers.dto.CurrentAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Main class for the Java Gatherers application.
 */
public class GathererWindowSliding {
    public static void main(String[] args) {
        CurrentAccount currentAccount = generateData(20);

        List<List<CurrentAccount.Transaction>> list = currentAccount.getTransactions().stream().gather(Gatherers.windowSliding(4)).toList();
        System.out.println("List of Transactions: " + list.size());

    }

    private static CurrentAccount generateData(int numberOfTransactions) {
        CurrentAccount.CurrentAccountBuilder accountBuilder =
                CurrentAccount.builder().name("Picpay").balance(new BigDecimal("5489.47"));

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
