import java.util.UUID;

public class User {
    private final Integer identifier;
    private String name;
    private Integer balance;
    private TransactionsLinkedList transactionsList;

    public User(UserIdsGenerator userIdsGenerator, String name, Integer balance) {
        this.identifier = userIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;
        this.transactionsList = new TransactionsLinkedList();
    }

    @Override
    public String toString() { return "Identifier: " + identifier + ", Name: " + name + ", Balance: " + balance; }

    public TransactionsLinkedList getTransactionsList() { return transactionsList; }

    public Transaction getTransactionsId(UUID id) {
        Transaction[] array = transactionsList.transformToArray();
        for (Transaction transaction: array) {
            if (transaction == null)
                break;
            if (transaction.getIdentifier().equals(id))
                return transaction;
        }
        return null;
    }

    public void setTransactionsList(TransactionsLinkedList transactionsList) { this.transactionsList = transactionsList; }

    public Integer getIdentifier() { return this.identifier; }

    public String getName() { return this.name; }

    public Integer getBalance() { return this.balance; }

    public void setName(String newName) { this.name = newName; }

    public void setBalance(Integer newBalance) { this.balance = newBalance; }
}
