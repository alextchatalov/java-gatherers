package com.example.javagatherers;

import com.example.javagatherers.dto.CurrentAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

/**
 * Main class for the Java Gatherers application.
 */
public class GathererCustom {
    public static void main(String[] args) {
        CurrentAccount currentAccount = generateData(20);

        currentAccount.getTransactions().stream()
                .gather(CustomGatherer.of(CurrentAccount.Transaction::getValue))
                .forEach(it -> System.out.println("Distinct Transaction: " + it.getChannel() + " - " + it.getValue()));

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

        CurrentAccount.Transaction duplecatedTransaction = CurrentAccount.Transaction.builder()
                .value(new BigDecimal(2))
                .channel(CurrentAccount.Channel.PIX)
                .build();
        transactions.add(duplecatedTransaction);

        return accountBuilder.transactions(transactions).build();
    }

    public static class DistinctTransactions<T, P> implements Gatherer<T, List<P>, T> {

        private final Function<T, P> selector;

        public DistinctTransactions(Function<T, P> selector) {
            this.selector = selector;
        }

        @Override
        public Supplier<List<P>> initializer() {
            return ArrayList::new;
        }

        @Override
        public Integrator<List<P>, T, T> integrator() {
            return Integrator.ofGreedy(((state, element, downstream) -> {
               P extracted = selector.apply(element);
               if (!state.contains(extracted)) {
                   state.add(extracted);
                   downstream.push(element);
               }
               return true;
            }));
        }
    }

    public class CustomGatherer {
        public static  <T, P> DistinctTransactions<T, P> of(Function<T, P> extractor) {
            return new DistinctTransactions<>(extractor);
        }
    }
}
