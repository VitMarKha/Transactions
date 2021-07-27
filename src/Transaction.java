import java.util.UUID;

enum TransferCategory {
    DEBITS,
    CREDITS
}

public class Transaction {
    private final UUID identifier;
    private User recipient;
    private User sender;
    private TransferCategory transferCategory;
    private Integer transferAmount;

    public Transaction(UUID newIdentifier, User newRecipient, User newSender, TransferCategory newTransferCategory, Integer newTransferAmount, boolean flagExecute) {
        this.identifier = newIdentifier;
        this.recipient = newRecipient;
        this.sender = newSender;
        this.transferCategory = newTransferCategory;
        this.transferAmount = newTransferAmount;
        if (flagExecute) {
            if (this.sender.getBalance() <= 0 || (this.sender.getBalance() - this.transferAmount) < 0)
                throw new RuntimeException("There is not enough money in the sender's account");
            this.sender.setBalance(this.sender.getBalance() - this.transferAmount);
            this.recipient.setBalance(this.recipient.getBalance() + this.transferAmount);
        }
    }

    @Override
    public String toString() {
        return "ID: " + identifier + ", Recipient: " + recipient.getName()
                + ", Sender: " + sender.getName() + ", TransferCategory: " + transferCategory
                + ", TransferAmount: " + transferAmount;
    }

    public UUID getIdentifier() { return this.identifier; }

    public User getRecipient() { return this.recipient; }

    public User getSender() { return this.sender; }

    public TransferCategory getTransferCategory() { return this.transferCategory; }

    public Integer getTransferAmount() { return this.transferAmount; }

    public void setRecipient(User recipient) { this.recipient = recipient; }

    public void setSender(User newSender) { this.sender = newSender; }

    public void setTransferCategory(TransferCategory transferCategory) { this.transferCategory = transferCategory; }

    public void setTransferAmount(Integer transferAmount) { this.transferAmount = transferAmount; }
}
