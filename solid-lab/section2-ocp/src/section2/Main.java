package section2;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SECTION 2: OCP ===");

        // 1. Calculate interest using Strategy Pattern (InterestPolicy interface)
        InterestPolicy savingsPolicy = new SavingsInterestPolicy();
        InterestPolicy currentPolicy = new CurrentInterestPolicy();
        InterestPolicy salaryPolicy = new SalaryInterestPolicy(); // New policy added cleanly!

        double sampleBalance = 1000.0;
        System.out.println("Savings Interest (4%): $" + savingsPolicy.calculate(sampleBalance));
        System.out.println("Current Interest (1%): $" + currentPolicy.calculate(sampleBalance));
        System.out.println("Salary Interest (5%):  $" + salaryPolicy.calculate(sampleBalance));

        // 2. Demonstrate Email notification injected into Bank
        System.out.println("\n-- Testing Email Notification Injection --");
        AccountRepository repo = new InMemoryAccountRepository();
        Bank bankWithEmail = new Bank(repo, new EmailNotificationService());
        bankWithEmail.openAccount(new Account(102, "Bob", 1200.0));

        // 3. Demonstrate that SMS notification can be substituted without editing Bank
        System.out.println("\n-- Testing SMS Notification Injection (Zero Bank edits!) --");
        Bank bankWithSMS = new Bank(repo, new SMSNotificationService());
        bankWithSMS.openAccount(new SalaryAccount(103, "Charlie", 2500.0));
    }
}
