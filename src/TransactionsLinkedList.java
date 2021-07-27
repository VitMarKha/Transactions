import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Node firstNode = new Node();
    private Node lastNode = new Node();
    private int size = 0;

    @Override
    public void addTransaction(Transaction transaction) {
        if (size == 0) {
            firstNode.transaction = transaction;
            firstNode.next = new Node();
            lastNode = firstNode.next;
            lastNode.next = null;
        }
        else {
            lastNode.transaction = transaction;
            lastNode.next = new Node();
            lastNode = lastNode.next;
            lastNode.next = null;
        }
        size += 1;
    }

    @Override
    public void removeTransactionByID(UUID id) {
        Node temp = firstNode;
        if (id.equals(firstNode.transaction.getIdentifier())) {
            firstNode = firstNode.next;
            size -= 1;
            return;
        }
        for (int i = 0; i < size; i++) {
            if (firstNode.next.transaction == null) {
                firstNode = temp;
                throw new TransactionNotFoundException();
            }
            if (id.equals(firstNode.next.transaction.getIdentifier())) {
                firstNode.next = firstNode.next.next;
                break;
            }
            else
                firstNode = firstNode.next;
        }
        size -= 1;
        firstNode = temp;
    }

    @Override
    public Transaction[] transformToArray() {
        Transaction[] array = new Transaction[size];
        Node temp = firstNode;
        for (int i = 0; i < size; i++) {
            if (firstNode == null)
                break;
            array[i] = firstNode.transaction;
            firstNode = firstNode.next;
        }
        firstNode = temp;
        return array;
    }

    public boolean hasTransaction(Transaction search) {
        Transaction[] array = this.transformToArray();
        for (Transaction transaction: array) {
            if (transaction.getIdentifier().equals(search.getIdentifier()))
                return true;
        }
        return false;
    }

    public int getSize() { return size; }

    private class Node {
        private Transaction transaction;
        private Node next;

        public Node() {
            this.transaction = null;
            this.next = null;
        }

        public void setTransaction(Transaction transaction) { this.transaction = transaction; }

        public void setNext(Node next) { this.next = next; }

        public Node getNext() { return next; }

        public Transaction getTransaction() { return transaction; }
    }

    class TransactionNotFoundException extends RuntimeException {
        public void printError() { System.out.println("TransactionNotFoundException"); }
    }
}
