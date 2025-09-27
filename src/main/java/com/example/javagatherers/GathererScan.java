import com.example.javagatherers.dto.Transaction;
import com.example.javagatherers.dto.TransactionStats;
import java.util.List;
import java.util.stream.Gatherers;

/**
 * Main class for the Java Gatherers application.
 */
void main() {
    List<Transaction> transactions = createTransactions();
   var result = transactions.stream().gather(Gatherers.scan(
           () -> new TransactionStats(0.0, 0.0, new HashMap<>(), 0),// Estado inicial
           (stats, transaction) -> { // stats = acomulado e transaction = elemente atual da lista
               double amount = transaction.amount();
               String category = transaction.category();
               Map<String, Double> updatedCategoryTotals = new HashMap<>(stats.categoryTotals());
               updatedCategoryTotals.merge(category, amount, Double::sum);


               double newTotalWithoutEntertainment = stats.totalWithoutEntertainment();
               if (!category.equals("entertainment")) {
                   newTotalWithoutEntertainment += amount;
               }

               double adjustedTotal = stats.totalAmount();

               // Exemplo: aplicar diferentes ajustes com base na categoria
               switch (category) {
                   case "groceries":
                       // 5% de cashback em supermercado
                       adjustedTotal += amount * 0.95;
                       break;
                   case "entertainment":
                       // Sem alteração para entretenimento
                       adjustedTotal += amount;
                       break;
                   case "utilities":
                       // Contas são contabilizadas integralmente
                       adjustedTotal += amount;
                       break;
                   default:
                       // 2% de cashback para outras categorias
                       adjustedTotal += amount * 0.98;
               }
               return new TransactionStats(
                       adjustedTotal,
                       newTotalWithoutEntertainment,
                       updatedCategoryTotals,
                       stats.transactionCount() + 1
               );
           }
   )).toList();

    System.out.println("Histórico de estatísticas das transações:");
    for (int i = 0; i < transactions.size(); i++) {
        Transaction tx = transactions.get(i);
        TransactionStats stats = result.get(i);

        System.out.println("\nApós transação " + tx.id() + " (" + tx.category() + "): R$" + tx.amount());
        System.out.println("  Total acumulado: R$" + String.format("%.2f", stats.totalAmount()));
        System.out.println("  Total sem entretenimento: R$" + String.format("%.2f", stats.totalWithoutEntertainment()));
        System.out.println("  Totais por categoria:");

        stats.categoryTotals().forEach((category, total) -> {
            System.out.println("    " + category + ": R$" + String.format("%.2f", total));
        });

        System.out.println("  Transações processadas: " + stats.transactionCount());
    }


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