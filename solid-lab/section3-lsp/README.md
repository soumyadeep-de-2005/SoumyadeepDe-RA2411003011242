# Section 3: Liskov Substitution Principle (LSP)

## Principle Overview
The **Liskov Substitution Principle (LSP)** states that objects of a superclass should be replaceable with objects of its subclasses without altering any of the desirable properties of the program (correctness, task performed, etc.). A subtype must adhere to the behavioral contract established by its abstraction.

> *Note: This is a standalone educational demonstration of LSP and is not part of the Global Digital Bank core engine.*

## What This Example Demonstrates
1. **The Classic Square / Rectangle Problem**:
   - Demonstrates how inheriting `Square` from `Rectangle` violates behavioral invariants: resizing width/height independently works on `Rectangle`, but breaks on `Square`, unexpectedly altering the area calculation from 200 to 400.
2. **Account Inheritance & `FixedDepositAccount`**:
   - Flawed approach: Forcing `FixedDepositAccount` to inherit a `withdraw()` method from `Account` and throwing `UnsupportedOperationException`. This breaks LSP because client code expecting an `Account` fails at runtime.
   - Refactored LSP approach: Segregating withdrawal capability into a dedicated `Withdrawable` interface. Accounts that support withdrawal (`SavingsAccount`, `CurrentAccount`) implement `Withdrawable`. `FixedDepositAccount` only implements base `Account` features without advertising an unsupported operation.

## Important Classes & Interfaces
- [`Rectangle.java`](src/section3/Rectangle.java) & [`Square.java`](src/section3/Square.java): Classic geometric LSP violation demonstration.
- [`Account.java`](src/section3/Account.java): Base account abstraction for balance and deposit.
- [`Withdrawable.java`](src/section3/Withdrawable.java): Focused capability interface declaring `void withdraw(double amount)`.
- [`SavingsAccount.java`](src/section3/SavingsAccount.java) & [`CurrentAccount.java`](src/section3/CurrentAccount.java): Implement both `Account` and `Withdrawable`.
- [`FixedDepositAccount.java`](src/section3/FixedDepositAccount.java): Inherits `Account`, but does NOT implement `Withdrawable`, honoring contract truthfulness.
- [`Main.java`](src/section3/Main.java): Demonstration runner comparing problematic designs against LSP-compliant substitution.

## How to Compile & Run
From `solid-lab/section3-lsp`:
```bash
javac -d bin $(find src -name "*.java")
java -cp bin section3.Main
```
Or from the repository root:
```bash
javac -d bin $(find solid-lab/section3-lsp/src -name "*.java")
java -cp bin section3.Main
```

### Expected Output
```text
=== SECTION 3: LSP ===
-- Classic Rectangle / Square Demonstration --
Expected Rectangle area (10 x 20): 200
Actual Square area produced:       400

-- Demonstrating the Problematic Design (List<Account>) --
Attempting withdrawal on account #201...
Withdrew $100.0 from account #201
Attempting withdrawal on account #202...
Caught exception as expected: Withdrawal is not supported on Fixed Deposit accounts!

-- Demonstrating Corrected LSP Design (List<Withdrawable>) --
Withdrew $100.0 from account #201
Withdrew $100.0 from account #203
```
