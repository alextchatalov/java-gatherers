import com.example.javagatherers.dto.Transaction;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    List<Transaction> transactions = createTransactions();

    List<Transaction> distinctTransactions = transactions.stream()
            .gather(new DistinctTransactions())
            .toList();

    IO.println("Transações originais: " + transactions.size());
    IO.println("Transações distintas: " + distinctTransactions.size());
    IO.println("\nTransações distintas:");
    distinctTransactions.forEach(tx ->
                    IO.println("""
                        ID: %s, Category: %s, Valor: R$%.2f
                        """.formatted(tx.id(), tx.category(), tx.amount()))
            );

}

// Input Elemento atual a ser processado, Lista de Estado - acumulador enquando o gatherer processa internamente e Output - Elemento de saida do gatherer
static class DistinctTransactions implements Gatherer<Transaction, Set<String>, Transaction> {


    // Initializar o estado do gatherer
    @Override
    public Supplier<Set<String>> initializer() {
        return HashSet::new;
    }

    // Integrar elemento atual com o estado do gatherer
    @Override
    public Gatherer.Integrator<Set<String>, Transaction, Transaction> integrator() {
        return (state, element, downstream) -> {
            // Se o ID da transação ainda não foi visto, processamos e adicionamos ao estado
            if (state.add(element.id())) {
                return downstream.push(element);
            }
            // Caso contrário, é uma duplicata e ignoramos
            return true;
        };
    }
}

private static List<Transaction> createTransactions() {
    return  List.of(
            new Transaction("tx1", 120.50, "groceries"),
            new Transaction("tx1", 120.50, "groceries"),
            new Transaction("tx2", 45.99, "entertainment"),
            new Transaction("tx3", 89.99, "utilities"),
            new Transaction("tx4", 35.45, "groceries"),
            new Transaction("tx5", 199.99, "electronics")
    );
}