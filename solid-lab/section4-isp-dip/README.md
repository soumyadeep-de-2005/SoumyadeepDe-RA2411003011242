# Section 4: Interface Segregation Principle (ISP) & Dependency Inversion Principle (DIP)

## Principle Overview
- **Interface Segregation Principle (ISP)**: Clients should not be forced to depend on interfaces they do not use. Prefer small, role-specific interfaces over large ("fat") monolithic interfaces.
- **Dependency Inversion Principle (DIP)**: High-level modules should not depend on low-level modules; both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

> *Note: This is a standalone educational demonstration of ISP and DIP and is not part of the Global Digital Bank core engine.*

## What This Example Demonstrates
1. **The Fat Interface Anti-Pattern (`fat_interface/`)**:
   - `BankService` bundled `deposit`, `withdraw`, `transfer`, `printStatement`, and `applyForLoan`.
   - `ATM` implementing `BankService` was forced to stub out or provide dummy implementations for operations its physical hardware cannot execute (`transfer`, `applyForLoan`).
2. **ISP Refactoring via Fine-Grained Capability Interfaces**:
   - Segregated into role interfaces: `Depositable`, `Withdrawable`, `Transferable`, `StatementProvider`, and `LoanEligible`.
   - `ATM` implements only `Depositable` and `Withdrawable`.
   - `SavingsAccount` implements only the operations it genuinely supports (`Depositable`, `Withdrawable`, `Transferable`, `StatementProvider`), leaving out loan processing.
3. **DIP: Decoupled Storage and Messaging**:
   - High-level orchestrator `Bank` depends strictly on abstractions: `AccountRepository` and `NotificationService`.
   - Swapping storage from `InMemoryAccountRepository` to `FileAccountRepository` requires zero changes to `Bank.java`.
   - Swapping notification channels requires zero changes to `Bank.java`.

## Important Classes & Interfaces
- [`fat_interface/BankService.java`](src/section4/fat_interface/BankService.java): Monolithic interface (anti-pattern).
- [`Depositable.java`](src/section4/Depositable.java): Single-method interface for deposits.
- [`Withdrawable.java`](src/section4/Withdrawable.java): Single-method interface for cash dispensing.
- [`Transferable.java`](src/section4/Transferable.java): Single-method interface for funds transfers.
- [`StatementProvider.java`](src/section4/StatementProvider.java): Single-method interface for statement generation.
- [`LoanEligible.java`](src/section4/LoanEligible.java): Single-method interface for credit underwriting.
- [`ATM.java`](src/section4/ATM.java): Focused terminal client using only `Depositable` and `Withdrawable`.
- [`AccountRepository.java`](src/section4/AccountRepository.java), [`InMemoryAccountRepository.java`](src/section4/InMemoryAccountRepository.java), [`FileAccountRepository.java`](src/section4/FileAccountRepository.java): Decoupled persistence strategies.
- [`Bank.java`](src/section4/Bank.java): High-level domain service adhering to DIP.
- [`Main.java`](src/section4/Main.java): Demonstration runner.

## How to Compile & Run
From `solid-lab/section4-isp-dip`:
```bash
javac -d bin $(find src -name "*.java")
java -cp bin section4.Main
```
Or from the repository root:
```bash
javac -d bin $(find solid-lab/section4-isp-dip/src -name "*.java")
java -cp bin section4.Main
```

### Expected Output
```text
=== SECTION 4: ISP + DIP ===
-- ISP: ATM using only Depositable and Withdrawable --
ATM [ATM-01] accepted cash deposit of $500.0
ATM [ATM-01] dispensed cash $200.0

-- ISP: SavingsAccount capabilities --
Deposited $600.0 into account #301
Withdrew $300.0 from account #301
Transferred $200.0 from account #301 to account #999
Savings Account Statement -> Account #301, Name: Grace, Balance: $4100.0

-- DIP: Bank with InMemoryAccountRepository --
Saving account to database
Sending: Account #401 opened for Henry

-- DIP: Swapping to FileAccountRepository (0 edits to Bank.java) --
Saving account to file (accounts.txt)
Sending: Account #402 opened for Isabel
Saving account to file (accounts.txt)
Sending: Deposit of $500.0 to account #402
```
