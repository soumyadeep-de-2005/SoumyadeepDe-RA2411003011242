package section2;

/**
 * High-level Bank service.
 * Accepts NotificationService via constructor injection (OCP & DIP).
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
            account.deposit(amount);
            repository.save(account);
            notificationService.send("Deposit of $" + amount + " to account #" + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(int accountNumber, double amount) {
        Account account = repository.findById(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            repository.save(account);
            notificationService.send("Withdrawal of $" + amount + " from account #" + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }
}
