import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService service = new TransactionsService();

    public void startProductionMode() {
        UserIdsGenerator userIdsGenerator = new UserIdsGenerator();
        while (true) {
            System.out.println("1. Add a user\n" +
                    "2. View user balances\n" +
                    "3. Perform a transfer\n" +
                    "4. View all transactions for a specific user\n" +
                    "5. Finish execution"
            );
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextInt()) {
                System.out.println("This is not a command");
                continue;
            }
            int command = scanner.nextInt();
            if (command == 5) {
                System.out.println("Goodbye :)");
                break;
            }
            else if (command == 1)
                addUserCommand(userIdsGenerator);
            else if (command == 2)
                viewUserBalances();
            else if (command == 3)
                performTransfer();
            else if (command == 4)
                viewAllTransactionsUser();
            else
                System.out.println("This is not a command");
        }
    }

    public void startDevMode() {
        UserIdsGenerator userIdsGenerator = new UserIdsGenerator();
        while (true) {
            System.out.println("1. Add a user\n" +
                            "2. View user balances\n" +
                            "3. Perform a transfer\n" +
                            "4. View all transactions for a specific user\n" +
                            "5. DEV - remove a transfer by ID\n" +
                            "6. DEV - check transfer validity\n" +
                            "7. Finish execution"
            );
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextInt()) {
                System.out.println("This is not a command");
                continue;
            }
            int command = scanner.nextInt();
            if (command == 7) {
                System.out.println("Goodbye :)");
                break;
            }
            else if (command == 1)
                addUserCommand(userIdsGenerator);
            else if (command == 2)
                viewUserBalances();
            else if (command == 3)
                performTransfer();
            else if (command == 4)
                viewAllTransactionsUser();
            else if (command == 5)
                removeTransferDev();
            else if (command == 6)
                checkTransferValidityDev();
            else
                System.out.println("This is not a command");
        }
    }

    private void addUserCommand(UserIdsGenerator userIdsGenerator) {
        System.out.println("Enter a User name and a Balance");
        try {
            Scanner scannerCommand = new Scanner(System.in);
            String commands = scannerCommand.nextLine();
            String[] arrCommands = commands.split(" ");
            User user = new User(userIdsGenerator, arrCommands[0], Integer.valueOf(arrCommands[1]));
            service.addingUser(user);
            System.out.println("User with ID = " + user.getIdentifier() + " is added, balance = " + user.getBalance() + "$");
        } catch (Exception e) {
            System.out.println("Not valid data");
        }
    }

    private void viewUserBalances() {
        try {
            System.out.println("Enter a user ID");
            Scanner scannerCommand = new Scanner(System.in);
            int id = scannerCommand.nextInt();
            System.out.println(service.getUserIntoId(id).getName() + " - " + service.getUserIntoId(id).getBalance());
        } catch (Exception e) {
            System.out.println("There is no user with this id");
        }
    }

    private void performTransfer() {
        try {
            System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
            Scanner scannerCommand = new Scanner(System.in);
            String commands = scannerCommand.nextLine();
            String[] arrCommands = commands.split(" ");
            service.makeTransaction(Integer.valueOf(arrCommands[0]), Integer.valueOf(arrCommands[1]), Integer.valueOf(arrCommands[2]));
            System.out.println("The transfer is completed");
        } catch (Exception e) {
            System.out.println("Not valid date or is not user with this id");
        }
    }

    private void viewAllTransactionsUser() {
        try {
            System.out.println("Enter a user ID");
            Scanner scannerCommand = new Scanner(System.in);
            int commands = scannerCommand.nextInt();
            Transaction[] transactions = service.getUserTransaction(service.getUserIntoId(commands));
            if (transactions.length == 0) {
                System.out.println("No transactions :(");
                System.out.println("------------------------");
                return;
            }
            for (Transaction transaction: transactions) {
                if (transaction == null)
                    break;
                System.out.println(transaction);
            }
            System.out.println("------------------------");
        } catch (Exception e) {
            System.out.println("Not valid date or is not user with this id");
        }
    }

    private void removeTransferDev() {
        try {
            System.out.println("Enter a user ID and a transfer ID");
            Scanner scanner = new Scanner(System.in);
            String commands = scanner.nextLine();
            String[] strCommands = commands.split(" ");
            UUID uuid = UUID.fromString(strCommands[1]);
            service.deleteTransaction(Integer.valueOf(strCommands[0]), uuid);
        } catch (Exception e) {
            System.out.println("Not valid date or is not user with this id");
        }
    }

    private void checkTransferValidityDev() {
        try {
            Transaction[] transactions = service.checkingTransactions();
            System.out.println("Check results:");
            if (transactions.length == 0) {
                System.out.println("No problems found");
                return;
            } else {
                for (Transaction transaction: transactions) {
                    if (transaction == null)
                        break;
                    System.out.println(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
