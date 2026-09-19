package section1;

/**
 * Responsible only for formatting account statements (SRP).
 */
public class StatementGenerator {

    public String generate(BankAccount account) {
        return "Statement: Account #" + account.getAccountNumber() +
               " (" + account.getName() + ") - Balance: $" + account.getBalance();
    }
}
