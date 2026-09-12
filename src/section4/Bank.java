package section4;

/**
 * High-level Bank service adhering to DIP:
 * - Depends strictly on AccountRepository and NotificationService abstractions.
 * - Receives dependencies via constructor injection.
 * - Zero direct instantiation of repository classes.
 */
public class Bank {
    private final AccountRepository repository;
    private final NotificationService notificationService;

    public Bank(AccountRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void openAccount(Account account) {
        repository.save(account);
        notificationService.send("Account #" + account.getAccountNumber() + " opened for " + account.getName());
    }

    public void deposit(int accountNumber, double amount) {
        Account account = repository.findById(accountNumber);
        if (account != null) {
            if (account instanceof Depositable depositable) {
                depositable.deposit(amount);
            }
            repository.save(account);
            notificationService.send("Deposit of $" + amount + " to account #" + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(int accountNumber, double amount) {
        Account account = repository.findById(accountNumber);
        if (account != null) {
            if (account instanceof Withdrawable withdrawable) {
                withdrawable.withdraw(amount);
            }
            repository.save(account);
            notificationService.send("Withdrawal of $" + amount + " from account #" + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public Account getAccount(int accountNumber) {
        return repository.findById(accountNumber);
    }
}
