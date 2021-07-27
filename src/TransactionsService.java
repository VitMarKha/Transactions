import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();
    private TransactionsLinkedList debit = new TransactionsLinkedList();
    private TransactionsLinkedList credit = new TransactionsLinkedList();

    public void addingUser(User user) { this.usersList.addUser(user); }

    public Integer getUserBalance(User user) { return this.usersList.retrieveUserByID(user.getIdentifier()).getBalance(); }

    public void makeTransaction(Integer recipientId, Integer senderId, Integer transferAmount) {
        UUID uuid = UUID.randomUUID();

        if (usersList.retrieveUserByID(senderId).getBalance() <= 0 || (usersList.retrieveUserByID(senderId).getBalance() - transferAmount) < 0)
            throw new IllegalTransactionException();

        Transaction transactionSend = new Transaction(uuid, usersList.retrieveUserByID(recipientId),
                usersList.retrieveUserByID(senderId), TransferCategory.CREDITS, transferAmount, true);

        usersList.retrieveUserByID(senderId).getTransactionsList().addTransaction(transactionSend);
        credit.addTransaction(transactionSend);

        Transaction transactionRecipient = new Transaction(uuid, usersList.retrieveUserByID(recipientId),
                usersList.retrieveUserByID(senderId), TransferCategory.DEBITS, transferAmount, false);

        usersList.retrieveUserByID(recipientId).getTransactionsList().addTransaction(transactionRecipient);
        debit.addTransaction(transactionRecipient);
    }

    public Transaction[] getUserTransaction(User user) { return user.getTransactionsList().transformToArray(); }

    public void deleteTransaction(Integer idUser, UUID idTransaction) {
        if (usersList.retrieveUserByID(idUser).getTransactionsId(idTransaction) == null)
            throw new TransactionsService.IllegalTransactionException();
        else if (usersList.retrieveUserByID(idUser).getTransactionsId(idTransaction).getTransferCategory() == TransferCategory.CREDITS)
            credit.removeTransactionByID(idTransaction);
        else if (usersList.retrieveUserByID(idUser).getTransactionsId(idTransaction).getTransferCategory() == TransferCategory.DEBITS)
            debit.removeTransactionByID(idTransaction);
        this.usersList.retrieveUserByID(idUser).getTransactionsList().removeTransactionByID(idTransaction);
    }

    public Transaction[] checkingTransactions() {
        TransactionsLinkedList unpairedTransactions = new TransactionsLinkedList();

        if (debit.getSize() == credit.getSize())
            return new Transaction[0];
        else if (debit.getSize() < credit.getSize()) {
            for (Transaction credTransaction: credit.transformToArray())
                if (!debit.hasTransaction(credTransaction))
                    unpairedTransactions.addTransaction(credTransaction);
        }
        else if (debit.getSize() > credit.getSize()) {
            for (Transaction debtTransaction: debit.transformToArray())
                if (!credit.hasTransaction(debtTransaction))
                    unpairedTransactions.addTransaction(debtTransaction);
        }
        return unpairedTransactions.transformToArray();
    }

    public User getUserIntoId(Integer id) { return usersList.retrieveUserByIndex(id); }

    class IllegalTransactionException extends RuntimeException {
        public void printError() { System.out.println("IllegalTransactionException"); }
    }
}
