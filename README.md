# Global Digital Bank + SOLID Lab

A unified enterprise-grade Java project repository consolidating **Global Digital Bank (Activities 1–14)** and the **SOLID Principles Lab (Sections 1–4)**.

---

## 1. Project Overview

This repository brings together two complementary software engineering curricula:
1. **Global Digital Bank (Activities 1–14)**: An enterprise banking domain model progressing from core Object-Oriented Programming (encapsulation, validation, polymorphism, custom exceptions, abstract account hierarchy) through to modern design patterns (Interface abstraction, Factory Pattern, Business Rules Engines, and Dynamic Configuration with Hot Reload).
2. **SOLID Lab (Sections 1–4)**: Focused educational demonstrations and reference implementations of the five core SOLID design principles (SRP, OCP, LSP, ISP, DIP).
3. **Integration Layer**: Demonstrates how the educational SOLID principles directly power the architecture of Global Digital Bank.

---

## 2. Repository Structure

```text
.
├── java-gdb-activities-10/                     # Global Digital Bank: Activities 1–10
│   ├── activity1/ ... activity1-solution/      # Activity 1: Encapsulation & Basic Account
│   ├── activity2/ ... activity2-solution/      # Activity 2: Account Suite & Status Validation
│   ├── activity3/ ... activity3-solution/      # Activity 3: PIN Verification & State Flow
│   ├── activity4/ ... activity4-solution/      # Activity 4: Comprehensive Account Test Suite
│   ├── activity5/ ... activity5-solution/      # Activity 5: Custom Exceptions Hierarchy
│   ├── activity6/ ... activity6-solution/      # Activity 6: Exception Handling Suite
│   ├── activity7/ ... activity7-solution/      # Activity 7: Account Subclasses (Savings, Current, FD, Salary)
│   ├── activity8/ ... activity8-solution/      # Activity 8: Polymorphism & Overdraft Mechanics
│   ├── activity9/ ... activity9-solution/      # Activity 9: AbstractAccount & Template Method Pattern
│   └── activity10/ ... activity10-solution/    # Activity 10: Complete Banking Operations Suite
│
├── java-gdb-activities-20/                     # Global Digital Bank: Activities 11–14 (SOLID Architecture)
│   ├── activity11/                             # Activity 11: IAccount Contract & AccountFactory (DIP/ISP)
│   ├── activity12/                             # Activity 12: Interface-Only Test Suite (Zero concrete imports)
│   ├── activity13/
│   │   ├── 13.1/                               # Activity 13.1: Centralized Rules Engine (SRP)
│   │   └── 13.2/                               # Activity 13.2: O(1) Map-Based Rules Engine + Tenure (OCP)
│   └── activity14/                             # Activity 14: External Properties & Hot Reload reloadRules()
│
├── solid-lab/                                  # Standalone SOLID Principles Demonstrations
│   ├── section1-srp/                           # Section 1: Single Responsibility Principle
│   ├── section2-ocp/                           # Section 2: Open/Closed Principle
│   ├── section3-lsp/                           # Section 3: Liskov Substitution Principle
│   ├── section4-isp-dip/                       # Section 4: Interface Segregation & Dependency Inversion
│   └── LAB_ANSWERS.md                          # Comprehensive answers to all lab questions
│
├── solid-integration/                          # Bridge & Integration Suite
│   ├── src/com/gdb/integration/
│   │   ├── capabilities/                       # Focused ISP Interfaces & Adapter
│   │   │   ├── Depositable.java
│   │   │   ├── Withdrawable.java
│   │   │   ├── StatementProvider.java
│   │   │   ├── Transferable.java
│   │   │   ├── LoanEligible.java
│   │   │   └── AccountCapabilityAdapter.java
│   │   └── BankSolidIntegrationTest.java       # End-to-end integration test driver
│   └── README.md
│
├── Global-Digital-Bank.pdf                     # Curriculum Reference Guide (Activities 1–10)
├── Global Digital Bank — Activities 11 to 14.pdf# Curriculum Reference Guide (Activities 11–14)
├── README.md                                   # This file
└── .gitignore                                  # Git exclusion rules
```

---

## 3. Global Digital Bank Architecture

### Activities 1–10: OOP Fundamentals
- **Encapsulation & State Validation** (`activity1`–`activity4`): Strict balance constraints, PIN protection, and account status transitions (`ACTIVE`, `SUSPENDED`, `CLOSED`).
- **Custom Banking Exceptions** (`activity5`–`activity6`): Robust domain exceptions (`InsufficientBalanceException`, `InvalidAmountException`, `InvalidPinException`, `InactiveAccountException`, `MinimumBalanceViolationException`).
- **Inheritance & Polymorphism** (`activity7`–`activity8`): Concrete account types (`SavingsAccount`, `CurrentAccount`, `FixedDepositAccount`, `SalaryAccount`) with specialized behavior such as overdraft limits and deposit locking.
- **Template Method Pattern** (`activity9`–`activity10`): Invariant debit logic handled in base `AbstractAccount`, delegating account-specific debit authorization via polymorphic hooks (`checkDebitAllowed()`).

### Activities 11–14: SOLID Evolution
- **Activity 11: `IAccount` & `AccountFactory`**:
  - `IAccount` provides a stable interface exposing only consumer-facing operations.
  - `AccountFactory` encapsulates instantiation logic, decoupling consumers from constructors.
- **Activity 12: Interface-Only Testing**:
  - Test suites import exclusively `IAccount` and `AccountFactory`. Zero imports or downcasting to concrete classes, strictly validating Dependency Inversion.
- **Activity 13.1: Account Rules Engine (SRP)**:
  - Business rules (minimum balances, tenure thresholds, overdraft calculations) are extracted out of domain models into `AccountRulesEngine`.
- **Activity 13.2: Map-Based Rules Engine (OCP)**:
  - Replaces nested `if-else` branching with $O(1)$ nested hash map lookups (`Map<String, Map<String, Rule>>`). New account types or policy tiers can be registered without modifying lookup routines.
- **Activity 14: External Configuration & Hot Reload**:
  - Properties files (`savings.properties`, `current.properties`, `fixeddeposit.properties`, `salary.properties`) drive engine behavior.
  - `AccountRulesEngine.reloadRules()` enables dynamic reconfiguration of interest rates and minimum balances at runtime without restarting the application.

---

## 4. SOLID Lab

Each section in `solid-lab/` provides an isolated demonstration with its own `README.md` and `Main` test driver:

### Section 1: Single Responsibility Principle (SRP)
- **Path**: [`solid-lab/section1-srp/`](solid-lab/section1-srp/)
- **Concept**: A class should have only one reason to change.
- **Implementation**: Deconstructs a monolithic `BankAccount` class into four focused entities:
  - `BankAccount` (account state & math)
  - `AccountRepository` (database persistence)
  - `NotificationService` (messaging/email)
  - `StatementGenerator` (statement presentation)

### Section 2: Open/Closed Principle (OCP)
- **Path**: [`solid-lab/section2-ocp/`](solid-lab/section2-ocp/)
- **Concept**: Open for extension, closed for modification.
- **Implementation**:
  - Uses the **Strategy Pattern** via `InterestPolicy` (`SavingsInterestPolicy`, `CurrentInterestPolicy`, `SalaryInterestPolicy`).
  - Implements **Constructor Injection** in `Bank`, allowing notification channels (`EmailNotificationService`, `SMSNotificationService`) to be swapped without modifying `Bank.java`.

### Section 3: Liskov Substitution Principle (LSP)
- **Path**: [`solid-lab/section3-lsp/`](solid-lab/section3-lsp/)
- **Concept**: Subtypes must be substitutable for their base types without altering correctness.
- **Implementation**:
  - Demonstrates why `Square` inheriting from `Rectangle` violates invariants.
  - Demonstrates why throwing `UnsupportedOperationException` in `FixedDepositAccount.withdraw()` violates LSP, and refactors withdrawal capability into a dedicated `Withdrawable` interface implemented only by accounts that support it.

### Section 4: Interface Segregation (ISP) & Dependency Inversion (DIP)
- **Path**: [`solid-lab/section4-isp-dip/`](solid-lab/section4-isp-dip/)
- **Concept**:
  - **ISP**: Avoid fat interfaces; clients should only depend on methods they need.
  - **DIP**: High-level modules must depend on abstractions, not concrete implementations.
- **Implementation**:
  - Replaces monolithic `BankService` with segregated interfaces: `Depositable`, `Withdrawable`, `Transferable`, `StatementProvider`, `LoanEligible`.
  - `Bank` depends strictly on `AccountRepository` and `NotificationService` abstractions, enabling persistence to swap from in-memory to file (`FileAccountRepository`) without changing `Bank.java`.

---

## 5. Integration: SOLID Principles in Global Digital Bank

The integration layer in [`solid-integration/`](solid-integration/) ties the two projects together:

| SOLID Principle | Realized in Global Digital Bank |
| :--- | :--- |
| **SRP** | Account entities (`SavingsAccount`, `CurrentAccount`) maintain account state and operations; `AccountRulesEngine` manages regulatory and business policies. |
| **OCP** | Map-based rules and external `.properties` files allow new tiers and policy values to be added or modified at runtime without altering core banking logic. |
| **LSP** | `SavingsAccount`, `CurrentAccount`, and `SalaryAccount` substitute uniformly for `IAccount` across transaction batches without breaking behavioral contracts. |
| **ISP** | Specialized terminals (such as an ATM or a passbook kiosk) interact via segregated capability interfaces (`Depositable`, `Withdrawable`, `StatementProvider`) via `AccountCapabilityAdapter` without polluting `IAccount`. |
| **DIP** | Consumer code and test suites depend strictly on `IAccount` and `AccountFactory`. No direct dependencies on concrete classes. |

---

## 6. How to Compile and Run

All components can be compiled and executed using standard `javac` and `java` commands from terminal or within IntelliJ IDEA / VS Code.

### Running SOLID Lab (Sections 1–4)

```bash
# Section 1 (SRP)
cd solid-lab/section1-srp
javac -d bin $(find src -name "*.java")
java -cp bin section1.Main
cd ../..

# Section 2 (OCP)
cd solid-lab/section2-ocp
javac -d bin $(find src -name "*.java")
java -cp bin section2.Main
cd ../..

# Section 3 (LSP)
cd solid-lab/section3-lsp
javac -d bin $(find src -name "*.java")
java -cp bin section3.Main
cd ../..

# Section 4 (ISP + DIP)
cd solid-lab/section4-isp-dip
javac -d bin $(find src -name "*.java")
java -cp bin section4.Main
cd ../..
```

### Running Global Digital Bank (Activities 11–14)

```bash
# Activity 11 (IAccount & AccountFactory)
cd java-gdb-activities-20/activity11
javac -d bin $(find src -name "*.java")
java -cp bin com.gdb.tests.TestInterfaceFactory
cd ../..

# Activity 12 (Interface-Only Factory Suite)
cd java-gdb-activities-20/activity12
javac -d bin $(find src -name "*.java")
java -cp bin com.gdb.tests.TestInterfaceFactory
cd ../..

# Activity 13.1 (Centralized Rules Engine)
cd java-gdb-activities-20/activity13/13.1
javac -d bin $(find src -name "*.java")
java -cp bin com.gdb.tests.TestAccountRulesEngine
cd ../../..

# Activity 13.2 (Map-Based Rules Engine)
cd java-gdb-activities-20/activity13/13.2
javac -d bin $(find src -name "*.java")
java -cp bin com.gdb.tests.TestAccountRulesEngine
cd ../../..

# Activity 14 (Properties-Driven Rules Engine & Hot Reload)
cd java-gdb-activities-20/activity14
javac -d bin $(find src -name "*.java")
java -cp bin com.gdb.tests.TestAccountRulesEngineProperties
cd ../..
```

### Running the End-to-End SOLID Integration Test

```bash
# 1. Compile Activity 14 dependencies
cd java-gdb-activities-20/activity14
javac -d bin $(find src -name "*.java")
cd ../..

# 2. Compile and run BankSolidIntegrationTest
cd solid-integration
javac -d bin -cp "../java-gdb-activities-20/activity14/bin:src" $(find src -name "*.java")
java -cp "bin:../java-gdb-activities-20/activity14/bin:../java-gdb-activities-20/activity14/src/main/resources" com.gdb.integration.BankSolidIntegrationTest
cd ..
```

---

## 7. Package Separation & Conflict Prevention

To ensure clean isolation and avoid duplicate class name conflicts:
- **Global Digital Bank**: Resides exclusively in `com.gdb.domain`, `com.gdb.exceptions`, `com.gdb.tests`, and `com.gdb.integration`.
- **SOLID Lab Demonstrations**: Reside in `section1`, `section2` (`section2.legacy`), `section3`, and `section4` (`section4.fat_interface`).
- No classes with shared names (e.g. `Account`, `SavingsAccount`, `CurrentAccount`, `Bank`) share a package namespace or cause build ambiguity.
