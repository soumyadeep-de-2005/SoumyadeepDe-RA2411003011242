# Global Digital Bank SOLID Integration Layer

## Overview
This integration module connects the educational SOLID principles demonstrated in `solid-lab` to the enterprise banking architecture of **Global Digital Bank** (`java-gdb-activities-20`).

Rather than modifying or disrupting the core domain classes (`IAccount`, `AbstractAccount`, `SavingsAccount`, etc.), this module demonstrates how:
1. **DIP**: Client code and test suites interact strictly through the `IAccount` contract and `AccountFactory`, completely decoupled from concrete account classes.
2. **LSP**: Subclasses (`SavingsAccount`, `CurrentAccount`, `SalaryAccount`, `FixedDepositAccount`) substitute cleanly for `IAccount` without runtime surprises.
3. **ISP**: Fine-grained role interfaces (`Depositable`, `Withdrawable`, `StatementProvider`, `Transferable`, `LoanEligible`) allow specialized clients (like ATMs or passbook printers) to consume only the methods they need via `AccountCapabilityAdapter`.
4. **SRP**: Account classes maintain account state while business policy is delegated to `AccountRulesEngine`.
5. **OCP & Hot Reload**: Policies defined in `savings.properties` can be hot-reloaded at runtime via `AccountRulesEngine.reloadRules()` without restarting the application.

## Directory Structure
```
solid-integration/
├── src/
│   └── com/gdb/integration/
│       ├── capabilities/
│       │   ├── Depositable.java               # ISP capability for deposits
│       │   ├── Withdrawable.java              # ISP capability for withdrawals
│       │   ├── StatementProvider.java         # ISP capability for statement views
│       │   ├── Transferable.java              # ISP capability for funds transfer
│       │   ├── LoanEligible.java              # ISP capability for credit facilities
│       │   └── AccountCapabilityAdapter.java  # Bridges IAccount to capability interfaces
│       └── BankSolidIntegrationTest.java      # End-to-end integration test driver
└── README.md
```

## How to Compile & Run the Integration Test

From the repository root:
```bash
# 1. Compile Activity 14 dependencies
cd java-gdb-activities-20/activity14 && javac -d bin $(find src -name "*.java")

# 2. Compile and run integration suite
cd ../../solid-integration
javac -d bin -cp "../java-gdb-activities-20/activity14/bin:src" $(find src -name "*.java")
java -cp "bin:../java-gdb-activities-20/activity14/bin:../java-gdb-activities-20/activity14/src/main/resources" com.gdb.integration.BankSolidIntegrationTest
```
