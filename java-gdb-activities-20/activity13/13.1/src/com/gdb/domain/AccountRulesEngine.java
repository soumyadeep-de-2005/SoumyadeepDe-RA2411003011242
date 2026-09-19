package com.gdb.domain;

import com.gdb.exceptions.*;
import java.util.HashMap;
import java.util.Map;

public class AccountRulesEngine {
    private static AccountRulesEngine instance;

    private static final Map<String, Double> SAVINGS_MIN_BALANCES = new HashMap<>();
    private static final Map<String, Double> SAVINGS_INTEREST_RATES = new HashMap<>();

    static {
        SAVINGS_MIN_BALANCES.put("NEW", 10000.0);
        SAVINGS_MIN_BALANCES.put("STANDARD", 7500.0);
        SAVINGS_MIN_BALANCES.put("PREMIUM", 5000.0);
        SAVINGS_MIN_BALANCES.put("PRIVILEGE", 2500.0);

        SAVINGS_INTEREST_RATES.put("NEW", 2.70);
        SAVINGS_INTEREST_RATES.put("STANDARD", 3.00);
        SAVINGS_INTEREST_RATES.put("PREMIUM", 3.50);
        SAVINGS_INTEREST_RATES.put("PRIVILEGE", 4.00);
    }

    public static synchronized AccountRulesEngine getInstance() {
        if (instance == null) {
            instance = new AccountRulesEngine();
        }
        return instance;
    }

    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) return "PRIVILEGE";
        if (tenureYears >= 3) return "PREMIUM";
        if (tenureYears >= 1) return "STANDARD";
        return "NEW";
    }

    public static double getSavingsMinBalance(int tenureYears) {
        if (tenureYears >= 5) return 2500.0;
        if (tenureYears >= 3) return 5000.0;
        if (tenureYears >= 1) return 7500.0;
        return 10000.0;
    }

    public static double getSavingsInterestRate(int tenureYears) {
        if (tenureYears >= 5) return 4.00;
        if (tenureYears >= 3) return 3.50;
        if (tenureYears >= 1) return 3.00;
        return 2.70;
    }

    public static double getMinimumBalance(String accountType) {
        if (accountType == null) return 0.0;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            return 1000.0;
        }
        return 0.0;
    }

    public static double getInterestRate(String accountType) {
        if (accountType == null) return 0.0;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            return 4.0;
        } else if ("FIXED_DEPOSIT".equalsIgnoreCase(accountType) || "FD".equalsIgnoreCase(accountType)) {
            return 6.5;
        }
        return 0.0;
    }

    public static double getOverdraftLimit(String accountType) {
        if (accountType == null) return 0.0;
        if ("CURRENT".equalsIgnoreCase(accountType)) {
            return 25000.0;
        }
        return 0.0;
    }

    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        return Math.max(25000.0, monthlyTurnover * 2.5);
    }

    public static double getFDInterestRate(int months) {
        if (months >= 36) return 7.50;
        if (months >= 12) return 6.50;
        return 5.00;
    }

    public static void validateWithdrawal(String accountType, double balance, double amount, double limit) throws AccountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (accountType == null) return;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            if ((balance - amount) < limit) {
                throw new MinimumBalanceViolationException("Cannot breach minimum balance of Rs " + limit);
            }
        } else if ("CURRENT".equalsIgnoreCase(accountType)) {
            if (amount > (balance + limit)) {
                throw new InsufficientBalanceException("Overdraft limit exceeded");
            }
        } else if ("FIXED_DEPOSIT".equalsIgnoreCase(accountType) || "FD".equalsIgnoreCase(accountType)) {
            throw new AccountException("Premature withdrawal not allowed on Fixed Deposit");
        } else if ("SALARY".equalsIgnoreCase(accountType)) {
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient funds in Salary account");
            }
        }
    }
}
