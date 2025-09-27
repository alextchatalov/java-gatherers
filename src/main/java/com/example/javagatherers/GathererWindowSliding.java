import com.example.javagatherers.dto.Transaction;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    createTransactions().stream().gather(Gatherers.windowSliding(2)).forEach(IO::println);

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
