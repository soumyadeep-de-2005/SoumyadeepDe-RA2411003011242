# Section 2: Open/Closed Principle (OCP)

## Legacy InterestCalculator Explanation
In the original implementation with an `if/else` chain:
```java
if ("Savings".equalsIgnoreCase(accountType)) {
    return balance * 0.04;
} else if ("Current".equalsIgnoreCase(accountType)) {
    return balance * 0.01;
}
```
If a fourth account type (such as `Salary` or `FixedDeposit`) is introduced:
1. We must open `InterestCalculator.java` and add another `else if` branch inside `calculate()`.
2. Modifying this existing class risks breaking previously working code for Savings or Current accounts and requires re-testing.

---

## OCP Solution (Strategy Pattern)
By defining the `InterestPolicy` interface:
```java
public interface InterestPolicy {
    double calculate(double balance);
}
```
We can add new interest policies like `SalaryInterestPolicy` (5%) as new classes without modifying any lines of code in `SavingsInterestPolicy` or `CurrentInterestPolicy`.

---

## Extensible Notifications via Constructor Injection
The `Bank` class accepts the `NotificationService` interface in its constructor:
```java
Bank bank = new Bank(repository, new EmailNotificationService());
```
When we need to switch to SMS notifications:
```java
Bank bank = new Bank(repository, new SMSNotificationService());
```
`Bank.java` itself requires **zero edits**, proving that it is open for extension but closed for modification.

---

## Checkpoint
Adding a new account type or interest rate never requires editing existing interest policy classes.
