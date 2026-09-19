# Section 2: Open/Closed Principle (OCP)

## Principle Overview
The **Open/Closed Principle (OCP)** states that software entities (classes, modules, functions) should be **open for extension**, but **closed for modification**. New functionality should be achievable by adding new code, not modifying tested existing code.

> *Note: This is a standalone educational demonstration of OCP and is not part of the Global Digital Bank core engine.*

## What This Example Demonstrates
1. **Strategy Pattern for Interest Calculation**:
   - The legacy `InterestCalculator` relied on fragile `if-else` chains switching on account type strings. Adding a new account type required modifying and risking regression in existing code.
   - Refactored via `InterestPolicy` interface. New policies (`SavingsInterestPolicy`, `CurrentInterestPolicy`, `SalaryInterestPolicy`) are separate implementations. Adding a new policy requires zero edits to existing policy classes.
2. **Notification Strategy via Dependency Injection**:
   - The `Bank` class depends on the `NotificationService` interface.
   - Switching from `EmailNotificationService` to `SMSNotificationService` is accomplished by injecting the new strategy at runtime without changing a single line inside `Bank.java`.

## Important Classes & Interfaces
- [`InterestPolicy.java`](src/section2/InterestPolicy.java): Strategy interface declaring `double calculate(double balance)`.
- [`SavingsInterestPolicy.java`](src/section2/SavingsInterestPolicy.java): 4% interest implementation.
- [`CurrentInterestPolicy.java`](src/section2/CurrentInterestPolicy.java): 1% interest implementation.
- [`SalaryInterestPolicy.java`](src/section2/SalaryInterestPolicy.java): 5% interest extension added with zero modifications to prior classes.
- [`Account.java`](src/section2/Account.java) & Subclasses: Domain models composing their respective `InterestPolicy`.
- [`NotificationService.java`](src/section2/NotificationService.java): Messaging contract.
- [`EmailNotificationService.java`](src/section2/EmailNotificationService.java) & [`SMSNotificationService.java`](src/section2/SMSNotificationService.java): Concrete notification strategies.
- [`Bank.java`](src/section2/Bank.java): Coordinator using constructor-injected repository and notification service.
- [`Main.java`](src/section2/Main.java): Demonstration runner.

## How to Compile & Run
From `solid-lab/section2-ocp`:
```bash
javac -d bin $(find src -name "*.java")
java -cp bin section2.Main
```
Or from the repository root:
```bash
javac -d bin $(find solid-lab/section2-ocp/src -name "*.java")
java -cp bin section2.Main
```

### Expected Output
```text
=== SECTION 2: OCP ===
Savings Interest (4%): $40.0
Current Interest (1%): $10.0
Salary Interest (5%):  $50.0

-- Testing Email Notification Injection --
Saving account to database
Sending: Account #102 opened for Bob

-- Testing SMS Notification Injection (Zero Bank edits!) --
Saving account to database
Sending SMS: Account #103 opened for Charlie
```
