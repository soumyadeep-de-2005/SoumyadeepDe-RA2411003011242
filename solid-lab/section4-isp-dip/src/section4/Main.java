package section4;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SECTION 4: ISP + DIP ===");

        // ============================================================
        // PART A — ISP (Interface Segregation Principle)
        // ============================================================
        System.out.println("-- ISP: ATM using only Depositable and Withdrawable --");
        ATM atm = new ATM("ATM-01", 10000.0);
        atm.deposit(500.0);
        atm.withdraw(200.0);

        System.out.println("\n-- ISP: SavingsAccount capabilities --");
        SavingsAccount savingsAccount = new SavingsAccount(301, "Grace", 4000.0);
        savingsAccount.deposit(600.0);
        savingsAccount.withdraw(300.0);
        savingsAccount.transfer(200.0, "999");
        savingsAccount.printStatement();

        // ============================================================
        // PART B — DIP (Dependency Inversion Principle)
        // ============================================================
        NotificationService notification = new EmailNotificationService();

        System.out.println("\n-- DIP: Bank with InMemoryAccountRepository --");
        AccountRepository memoryRepo = new InMemoryAccountRepository();
        Bank memoryBank = new Bank(memoryRepo, notification);
        memoryBank.openAccount(new SavingsAccount(401, "Henry", 1200.0));

        System.out.println("\n-- DIP: Swapping to FileAccountRepository (0 edits to Bank.java) --");
        // To switch to a file-based repository, ONLY this one line is modified:
        AccountRepository fileRepo = new FileAccountRepository("accounts.txt");
        Bank fileBank = new Bank(fileRepo, notification);
        fileBank.openAccount(new SavingsAccount(402, "Isabel", 2500.0));
        fileBank.deposit(402, 500.0);
    }
}
