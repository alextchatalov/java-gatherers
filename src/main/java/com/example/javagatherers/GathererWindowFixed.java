import com.example.javagatherers.dto.CurrentAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    CurrentAccount currentAccount = generateData(20);

    currentAccount.getTransactions().stream().gather(Gatherers.windowFixed(10)).forEach(it -> {
        System.out.println(it.size());
    });

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
