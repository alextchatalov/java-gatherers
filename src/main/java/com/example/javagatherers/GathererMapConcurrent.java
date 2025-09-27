import com.example.javagatherers.dto.Transaction;
import java.util.List;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    List<Transaction> transactions = createTransactions();
    var result = transactions.stream().gather(Gatherers.mapConcurrent(4, currentTransaction -> // Limita a 4 threads concorrentes
            "Processing transaction: %s on thread %s".formatted(currentTransaction.id(), Thread.currentThread().threadId()))); // Processa cada transação e retorna uma string com o ID da transação e o ID da thread
    result.forEach(IO::println);
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