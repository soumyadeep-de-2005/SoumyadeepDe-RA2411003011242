package com.gdb.tests;

import com.gdb.domain.IAccount;
import com.gdb.domain.AccountFactory;
import com.gdb.exceptions.*;

public class TestInterfaceFactory {
    public static void main(String[] args) {
        System.out.println("=== Activity 12: Factory-Driven System Suite ===");

        // Test 1: Savings Account Creation & Deposit
        try {
            IAccount savings = AccountFactory.createAccount("SAVINGS", "SAV1001", "Rajesh Sharma", 28, 5000.0, "ACTIVE", "1234");
            if (!"SAVINGS".equalsIgnoreCase(savings.getAccountType())) {
                throw new AssertionError("Account type mismatch");
            }
            if (savings.getBalance() != 5000.0) {
                throw new AssertionError("Initial balance mismatch");
            }
            savings.deposit(1000.0);
            if (savings.getBalance() != 6000.0) {
                throw new AssertionError("Deposit failed to update balance correctly");
            }
            // Verify minimum balance rule enforcement through interface
            boolean minBalanceBlocked = false;
            try {
                // Balance is 6000. Min balance is 1000. Withdrawing 5500 would leave 500 (< 1000).
                savings.withdraw(5500.0, "1234");
            } catch (MinimumBalanceViolationException e) {
                minBalanceBlocked = true;
            }
            if (!minBalanceBlocked) {
                throw new AssertionError("Expected minimum balance violation");
            }
            System.out.println("[Test 1] Savings Account Creation & Deposit: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 1] Savings Account Creation & Deposit: [FAIL] - " + e.getMessage());
        }

        // Test 2: Current Account Overdraft Withdrawal
        try {
            IAccount current = AccountFactory.createAccount("CURRENT", "CUR1001", "Priya Patel", 34, 10000.0, "ACTIVE", "5678");
            current.deposit(2000.0); // balance becomes 12000.0
            // Overdraft limit is 25000.0. Withdrawing 15000.0 results in -3000.0 balance.
            current.withdraw(15000.0, "5678");
            if (current.getBalance() != -3000.0) {
                throw new AssertionError("Overdraft balance mismatch: expected -3000.0 but got " + current.getBalance());
            }

            // Exceed overdraft limit: balance is -3000, limit is 25000 (max withdrawal remaining is 22000).
            boolean overdraftBlocked = false;
            try {
                current.withdraw(25000.0, "5678");
            } catch (InsufficientBalanceException e) {
                overdraftBlocked = true;
            }
            if (!overdraftBlocked) {
                throw new AssertionError("Expected overdraft limit violation");
            }
            System.out.println("[Test 2] Current Account Overdraft Withdrawal: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 2] Current Account Overdraft Withdrawal: [FAIL] - " + e.getMessage());
        }

        // Test 3: Fixed Deposit Premature Withdrawal Block
        try {
            IAccount fd = AccountFactory.createAccount("FIXED_DEPOSIT", "FD1001", "Amit Kumar", 45, 50000.0, "ACTIVE", "1111");
            boolean prematureWithdrawalBlocked = false;
            try {
                fd.withdraw(5000.0, "1111");
            } catch (AccountException e) {
                prematureWithdrawalBlocked = true;
            }
            if (!prematureWithdrawalBlocked) {
                throw new AssertionError("Expected Fixed Deposit premature withdrawal exception");
            }
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL] - " + e.getMessage());
        }

        // Test 4: Invalid Type Rejection (and also verify Salary account)
        try {
            // Verify Salary account creation and transactions work through interface
            IAccount salary = AccountFactory.createAccount("SALARY", "SAL1001", "Sneha Verma", 26, 30000.0, "ACTIVE", "2222");
            salary.deposit(5000.0);
            salary.withdraw(10000.0, "2222");
            if (salary.getBalance() != 25000.0) {
                throw new AssertionError("Salary account balance mismatch");
            }

            // Negative test: invalid account type
            boolean invalidTypeRejected = false;
            try {
                AccountFactory.createAccount("UNKNOWN_TYPE", "UNK1001", "Ghost", 30, 1000.0, "ACTIVE", "0000");
            } catch (IllegalArgumentException e) {
                invalidTypeRejected = true;
            }
            if (!invalidTypeRejected) {
                throw new AssertionError("Expected IllegalArgumentException for unknown account type");
            }
            System.out.println("[Test 4] Invalid Type Rejection: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 4] Invalid Type Rejection: [FAIL] - " + e.getMessage());
        }

        System.out.println("Factory-driven architecture successfully verified!");
    }
}
