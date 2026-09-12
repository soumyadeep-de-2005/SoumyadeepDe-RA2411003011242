package section1;

/**
 * ============================================================================
 * SECTION 1 — SRP: "The class that does everything"
 * ============================================================================
 * 
 * TASK 1: Four distinct reasons why BankAccount would need to change:
 * 1. Database requirements change (e.g. changing SQL schema, or switching to ORM/file storage).
 * 2. Email provider changes (e.g. switching email APIs or notification message formats).
 * 3. Statement format changes (e.g. altering statement layout, columns, or formatting).
 * 4. Account operation rules change (e.g. updating withdrawal rules, overdraft rules, or balance limits).
 * 
 * TASK 2: One-line job description:
 * "BankAccount is responsible only for managing account state and performing core account operations: deposit, withdraw, and get balance."
 * 
 * CHECKPOINT:
 * BankAccount only contains account-related state, deposit(), withdraw(), and simple getters.
 */
public class BankAccount {
    private int accountNumber;
    private String name;
    private double balance;

    public BankAccount(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " into account #" + accountNumber);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance in account #" + accountNumber);
        } else {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from account #" + accountNumber);
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
