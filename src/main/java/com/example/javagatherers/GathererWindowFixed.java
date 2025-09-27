import com.example.javagatherers.dto.CurrentAccount;
import com.example.javagatherers.dto.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    List<Transaction> transactions = createTransactions();

    List<List<Transaction>> windows = transactions.stream().gather(Gatherers.windowFixed(
            3)).toList();

    windows.stream().forEach(window -> {
        IO.println("Window:");
        window.forEach(tx -> IO.println("  " + tx));
    });

}

private static List<Transaction> createTransactions() {
    return  List.of(
            new Transaction("tx1", 120.50, "groceries"),
            new Transaction("tx2", 45.99, "entertainment"),
            new Transaction("tx3", 89.99, "utilities"),
            new Transaction("tx4", 35.45, "groceries"),
            new Transaction("tx5", 199.99, "electronics")
    );
}
