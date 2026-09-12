# Java SOLID Principles Lab Answers

---

## SECTION 1 — SRP (Single Responsibility Principle)

### Task 1: Distinct Reasons BankAccount Would Need to Change
1. **Database / Persistence changes**: If storage changes (e.g., from JDBC to Hibernate or file storage), the saving logic must change.
2. **Notification / Email changes**: If the email vendor changes or notification templates change, the email-sending code must change.
3. **Statement formatting changes**: If the bank changes how account statements look (layout, headers, formatting), statement formatting code must change.
4. **Account transaction rules**: If withdrawal rules, minimum balances, or deposit rules change, transaction methods must change.

### Task 2: One-Line Job Description for BankAccount
> "BankAccount is responsible only for managing account state and performing core account operations: deposit, withdraw, and get balance."

### Task 5: Wrap-up Explanation
Separating responsibilities produced 4 classes: `BankAccount`, `AccountRepository`, `NotificationService`, and `StatementGenerator`. This separation makes testing much easier because `BankAccount` can be unit tested quickly in memory without needing to set up a database connection or email server. It also makes `BankAccount` easier to maintain because modifying statement layouts or notification mechanisms will never accidentally break financial calculations or account balances.

---

## SECTION 2 — OCP (Open/Closed Principle)

### Legacy InterestCalculator
In the original implementation with an `if/else` chain:
```java
if ("Savings".equalsIgnoreCase(accountType)) {
    return balance * 0.04;
} else if ("Current".equalsIgnoreCase(accountType)) {
    return balance * 0.01;
}
```
If we want to add a 4th account type (e.g. `FixedDeposit` or `Salary`), we have to:
1. Open the existing `InterestCalculator.java` file and add another `else if` branch inside `calculate()`.
2. Re-test all previous account types to ensure we did not introduce syntax errors or logic regressions.

### OCP Solution (Strategy Pattern)
Instead of editing existing code, we defined the `InterestPolicy` interface:
```java
public interface InterestPolicy {
    double calculate(double balance);
}
```
Now, new account types such as `SalaryAccount` and their rates (`SalaryInterestPolicy` at 5%) are added as brand new classes implementing `InterestPolicy`. Zero lines of code were modified in `SavingsInterestPolicy` or `CurrentInterestPolicy`.

Similarly, `Bank` takes `NotificationService` as a constructor parameter. We can switch from `EmailNotificationService` to `SMSNotificationService` simply by passing a different object into `new Bank(...)`, leaving `Bank.java` completely untouched.

---

## SECTION 3 — LSP (Liskov Substitution Principle)

### Task 1: Rectangle / Square Problem Explanation
The Rectangle/Square example violates LSP because calling code assumes that changing a rectangle's width does not alter its height. When `Square` inherits from `Rectangle` and overrides `setWidth()` and `setHeight()` to keep both sides equal, setting width to 10 and height to 20 makes both sides 20, yielding an area of 400 instead of the expected 200. Because a `Square` cannot substitute for a `Rectangle` without breaking expected behavior, the inheritance hierarchy violates LSP.

### Task 4: Why Throwing an Exception is NOT a Correct LSP Solution
Making `FixedDepositAccount` implement `Withdrawable` and simply throwing `UnsupportedOperationException` is not a valid LSP solution because it breaks the fundamental substitution rule: any subtype must be usable wherever its parent type or interface is expected without causing errors. If `Withdrawable` promises a `withdraw()` method, client code looping over withdrawable accounts expects the method to work safely. Throwing an unsupported exception forces the caller to write messy `try-catch` blocks or `instanceof` checks, defeating the purpose of polymorphism. The proper fix is to only have genuinely withdrawable accounts implement `Withdrawable`.

---

## SECTION 4 — ISP + DIP (Interface Segregation & Dependency Inversion)

### Part A — ISP: Fat Interface Problem in ATM
The fat `BankService` interface contained `deposit`, `withdraw`, `transfer`, `printStatement`, and `applyForLoan`. When `ATM` was forced to implement `BankService`, it had to stub out:
- `transfer`: Physical ATM hardware does not handle wire transfers between external accounts.
- `printStatement`: Basic ATM terminals do not always have full statement printers.
- `applyForLoan`: ATM firmware cannot review loan applications or assess creditworthiness.

By splitting `BankService` into small capability interfaces (`Depositable`, `Withdrawable`, `Transferable`, `StatementProvider`, `LoanEligible`), `ATM` only implements `Depositable` and `Withdrawable`, while `SavingsAccount` implements the interfaces it actually supports.

### Part B — DIP: Decoupling Storage
`Bank.java` does not instantiate a concrete repository such as `new InMemoryAccountRepository()`. Instead, it declares:
```java
private final AccountRepository repository;
private final NotificationService notificationService;

public Bank(AccountRepository repository, NotificationService notificationService) {
    this.repository = repository;
    this.notificationService = notificationService;
}
```
When we migrated from in-memory storage to `FileAccountRepository`, only the single instantiation line in `Main.java` was changed:
```java
AccountRepository repo = new FileAccountRepository("accounts.txt");
```
`Bank.java` required zero code changes because it depends on the abstraction (`AccountRepository`), not the concrete implementation.

### Task 5: Reflection on Adding New Requirements
If we were asked today to add a `MongoAccountRepository` or a `WhatsAppNotificationService`, we would only need to create a single new class implementing `AccountRepository` or `NotificationService` and pass it into the constructor in `Main.java`. High-level business logic in `Bank.java` and all account classes would remain completely untouched with zero risk of regression. In contrast, in the original monolithic `BankAccount` from Lab 1, adding MongoDB or WhatsApp would have required opening and modifying the single giant `BankAccount` class, risking accidental breakage of core withdrawal math, balance tracking, or statement formatting.
