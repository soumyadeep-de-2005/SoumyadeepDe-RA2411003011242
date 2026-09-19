package section3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SECTION 3: LSP ===");

        // 1. Classic Rectangle / Square demonstration
        System.out.println("-- Classic Rectangle / Square Demonstration --");
        Rectangle rectangle = new Square();
        rectangle.setWidth(10);
        rectangle.setHeight(20);
        System.out.println("Expected Rectangle area (10 x 20): 200");
        System.out.println("Actual Square area produced:       " + rectangle.area());

        // 2. Demonstrate the bad FixedDepositAccount idea safely
        System.out.println("\n-- Demonstrating the Problematic Design (List<Account>) --");
        List<Account> allAccounts = new ArrayList<>();
        allAccounts.add(new SavingsAccount(201, "Dave", 1500.0));
        allAccounts.add(new FixedDepositAccount(202, "Eve", 50000.0));

        for (Account acc : allAccounts) {
            try {
                System.out.println("Attempting withdrawal on account #" + acc.getAccountNumber() + "...");
                acc.withdraw(100.0);
            } catch (UnsupportedOperationException e) {
                System.out.println("Caught exception as expected: " + e.getMessage());
            }
        }

        // 3. Demonstrate corrected Withdrawable design
        System.out.println("\n-- Demonstrating Corrected LSP Design (List<Withdrawable>) --");
        List<Withdrawable> withdrawableAccounts = new ArrayList<>();
        withdrawableAccounts.add(new SavingsAccount(201, "Dave", 1500.0));
        withdrawableAccounts.add(new CurrentAccount(203, "Frank", 3000.0));
        // FixedDepositAccount does NOT implement Withdrawable,
        // so the compiler prevents it from being added to this list!

        for (Withdrawable w : withdrawableAccounts) {
            w.withdraw(100.0);
        }
    }
}
