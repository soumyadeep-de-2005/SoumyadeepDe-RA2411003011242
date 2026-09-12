package section1;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SECTION 1: SRP ===");

        // 1. Create a BankAccount
        BankAccount account = new BankAccount(101, "Alice", 500.0);

        // 2. Perform deposits and withdrawals
        account.deposit(200.0);
        account.withdraw(100.0);

        // 3. Use AccountRepository to save the account
        AccountRepository repo = new AccountRepository();
        repo.save(account);

        // 4. Use NotificationService to send notification
        NotificationService notification = new NotificationService();
        notification.send("Account #101 balance updated to $" + account.getBalance());

        // 5. Use StatementGenerator to format and print statement
        StatementGenerator generator = new StatementGenerator();
        System.out.println(generator.generate(account));
    }
}
