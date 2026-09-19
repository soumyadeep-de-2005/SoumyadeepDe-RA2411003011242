# Section 1: Single Responsibility Principle (SRP)

## Principle Overview
The **Single Responsibility Principle (SRP)** states that a class should have one, and only one, reason to change. Each class should encapsulate a single, well-defined responsibility.

> *Note: This is a standalone educational demonstration of SRP and is not part of the Global Digital Bank core engine.*

## What This Example Demonstrates
Originally, an all-in-one `BankAccount` class handled account state, database persistence, email notifications, and statement formatting. 

In this refactored design, responsibilities are cleanly decoupled into 4 focused classes:
1. **`BankAccount`**: Responsible *only* for managing account state and executing core financial transactions (`deposit()`, `withdraw()`, and balance inquiry).
2. **`AccountRepository`**: Responsible for data storage and persistence (`save()`, `find()`).
3. **`NotificationService`**: Responsible for messaging/email delivery (`sendEmail()`).
4. **`StatementGenerator`**: Responsible for formatting and outputting account statements (`generateStatement()`).

## Important Classes
- [`BankAccount.java`](src/section1/BankAccount.java): Core domain entity holding balance, account number, and name.
- [`AccountRepository.java`](src/section1/AccountRepository.java): Handles persistence operations.
- [`NotificationService.java`](src/section1/NotificationService.java): Handles communication operations.
- [`StatementGenerator.java`](src/section1/StatementGenerator.java): Formats account statement presentation.
- [`Main.java`](src/section1/Main.java): Demonstration test harness coordinating the components.

## How to Compile & Run
From `solid-lab/section1-srp`:
```bash
javac -d bin $(find src -name "*.java")
java -cp bin section1.Main
```
Or from the repository root:
```bash
javac -d bin $(find solid-lab/section1-srp/src -name "*.java")
java -cp bin section1.Main
```

### Expected Output
```text
=== SECTION 1: SRP ===
Deposited $200.0 into account #101
Withdrew $100.0 from account #101
Saving account to database
Sending: Account #101 balance updated to $600.0
Statement: Account #101 (Alice) - Balance: $600.0
```
