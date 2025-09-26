import com.example.javagatherers.dto.CurrentAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    CurrentAccount currentAccount = generateData(20);

    List<BigDecimal> runningBalances = currentAccount.getTransactions().stream()
            .gather(Gatherers.scan(
                    currentAccount::getBalance,
                    (BigDecimal running, CurrentAccount.Transaction tx) -> running.add(tx.getValue())
            ))
            .toList();

    printResult(currentAccount, runningBalances);
}

private static void printResult(CurrentAccount currentAccount, List<BigDecimal> runningBalances) {
    IntStream.range(0, currentAccount.getTransactions().size())
            .forEach(i -> {
                CurrentAccount.Transaction tx = currentAccount.getTransactions().get(i);
                BigDecimal balanceAfter = runningBalances.get(i);
                System.out.println(formatTransactionDetails(i + 1, tx, balanceAfter));
            });
}

private static String formatTransactionDetails(int txNumber, CurrentAccount.Transaction tx, BigDecimal balance) {
    return String.format("Tx %d | Channel=%s | Value=%s | Running balance=%s",
            txNumber,
            tx.getChannel(),
            tx.getValue(),
            balance);
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
