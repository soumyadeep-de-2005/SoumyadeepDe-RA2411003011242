package section4;

/**
 * SavingsAccount implements only the capability interfaces it genuinely supports:
 * Depositable, Withdrawable, Transferable, StatementProvider.
 * It is not forced to implement LoanEligible.
 */
public class SavingsAccount extends Account implements Depositable, Withdrawable, Transferable, StatementProvider {

    public SavingsAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " into account #" + getAccountNumber());
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from account #" + getAccountNumber());
        }
    }

    @Override
    public void transfer(double amount, String toAccount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Transferred $" + amount + " from account #" + getAccountNumber() + " to account #" + toAccount);
        }
    }

    @Override
    public void printStatement() {
        System.out.println("Savings Account Statement -> Account #" + getAccountNumber() +
                           ", Name: " + getName() + ", Balance: $" + getBalance());
    }
}
