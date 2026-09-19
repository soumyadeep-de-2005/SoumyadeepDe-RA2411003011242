# Section 4: Interface Segregation Principle (ISP) + Dependency Inversion Principle (DIP)

## Part A — ISP: The Fat Interface Problem
The fat `BankService` interface defined five methods: `deposit`, `withdraw`, `transfer`, `printStatement`, and `applyForLoan`.
When `ATM` implemented `BankService`, it was forced to provide implementations for methods it did not support:
- `transfer`: Physical ATM hardware does not handle wire transfers between external accounts.
- `printStatement`: Basic ATM terminals might lack receipt or statement printing capabilities.
- `applyForLoan`: ATM firmware cannot review loan applications or perform underwriting.

By breaking `BankService` into fine-grained interfaces (`Depositable`, `Withdrawable`, `Transferable`, `StatementProvider`, `LoanEligible`), `ATM` only implements `Depositable` and `Withdrawable`, while `SavingsAccount` implements only the capabilities it genuinely supports.

---

## Part B — DIP: Decoupling Storage
In `Bank.java`, instead of hardcoding `new InMemoryAccountRepository()`, `Bank` depends on the `AccountRepository` interface:
```java
private final AccountRepository repository;
private final NotificationService notificationService;

public Bank(AccountRepository repository, NotificationService notificationService) {
    this.repository = repository;
    this.notificationService = notificationService;
}
```
When switching to `FileAccountRepository`, only the line instantiating the repository in `Main.java` was changed:
```java
AccountRepository fileRepo = new FileAccountRepository("accounts.txt");
```
`Bank.java` required **zero edits** because it depends upon an abstraction, not a concrete class.

---

## Task 5: Wrap-up Reflection
If we were asked today to add a `MongoAccountRepository` or a `WhatsAppNotificationService`, we would only need to create a single new class implementing `AccountRepository` or `NotificationService` and pass it into the constructor in `Main.java`. High-level business logic in `Bank.java` and all account classes would remain completely untouched with zero risk of regression. In contrast, in the original monolithic `BankAccount` from Lab 1, adding MongoDB or WhatsApp would have required opening and modifying the single giant `BankAccount` class, risking accidental breakage of core withdrawal math, balance tracking, or statement formatting.

---

## Checkpoint
`Bank` depends only on interfaces (`AccountRepository`, `NotificationService`); every class implements only the capability-interfaces it genuinely needs.
