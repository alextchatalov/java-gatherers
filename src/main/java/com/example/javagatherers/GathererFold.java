import com.example.javagatherers.dto.CategoryTotal;
import com.example.javagatherers.dto.Transaction;

import java.util.List;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    var transactions =createTransactions();
    List<CategoryTotal> categoryTotals = transactions.stream().gather(Gatherers.fold( // Usando o gatherer fold para agregar transações por categoria
            () -> new ArrayList<CategoryTotal>(), // Inicia uma nova Lista para salvar os totais por categoria
            (acc, tx) -> { // Para cada transação, atualiza a lista de totais por categoria
                for (int i = 0; i < acc.size(); i++) {
                    CategoryTotal ct = acc.get(i);
                    if (ct.category().equals(tx.category())) {
                        acc.set(i, new CategoryTotal(ct.category(), ct.total() + tx.amount(), ct.count() + 1)); // Atualiza o total e a contagem
                        return acc;
                    }
                }
                acc.add(new CategoryTotal(tx.category(), tx.amount(), 1)); // Adiciona uma nova categoria se não existir
                return acc;
            }
    )).flatMap(ArrayList::stream).toList(); // Converte a lista de listas em uma lista simples


    categoryTotals.forEach(ct ->
            IO.println("""
                        Categoria: %s, Total: R$%.2f, Quantidade: %d
                        """.formatted(ct.category(), ct.total(), ct.count()))
    );
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