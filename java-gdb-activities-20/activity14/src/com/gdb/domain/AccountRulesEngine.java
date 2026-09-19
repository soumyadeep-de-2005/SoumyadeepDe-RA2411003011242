package com.gdb.domain;

import com.gdb.exceptions.*;

public class AccountRulesEngine {
    private static AccountRulesEngine instance;

    private static AccountRulesPropertiesLoader savingsLoader =
        new AccountRulesPropertiesLoader("src/main/resources/config/rules/savings.properties");
    private static AccountRulesPropertiesLoader currentLoader =
        new AccountRulesPropertiesLoader("src/main/resources/config/rules/current.properties");
    private static AccountRulesPropertiesLoader fdLoader =
        new AccountRulesPropertiesLoader("src/main/resources/config/rules/fixeddeposit.properties");
    private static AccountRulesPropertiesLoader salaryLoader =
        new AccountRulesPropertiesLoader("src/main/resources/config/rules/salary.properties");

    public static synchronized AccountRulesEngine getInstance() {
        if (instance == null) {
            instance = new AccountRulesEngine();
        }
        return instance;
    }

    public static synchronized void reloadRules() {
        savingsLoader.reload();
        currentLoader.reload();
        fdLoader.reload();
        salaryLoader.reload();
    }

    // Bucket names are lowercase so they match the keys in savings.properties (e.g. min.balance.new).
    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) return "privilege";
        if (tenureYears >= 3) return "premium";
        if (tenureYears >= 1) return "standard";
        return "new";
    }

    public static double getSavingsMinBalance(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        return savingsLoader.getDouble("min.balance." + bucket, 10000.0);
    }

    public static double getSavingsInterestRate(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        return savingsLoader.getDouble("interest.rate." + bucket, 2.70);
    }

    public static double getMinimumBalance(String accountType) {
        if (accountType == null) return 0.0;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            return savingsLoader.getDouble("min.balance.standard", 1000.0);
        }
        return 0.0;
    }

    public static double getInterestRate(String accountType) {
        if (accountType == null) return 0.0;
        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            return savingsLoader.getDouble("interest.rate.standard", 4.0);
        } else if ("FIXED_DEPOSIT".equalsIgnoreCase(accountType) || "FD".equalsIgnoreCase(accountType)) {
            return fdLoader.getDouble("interest.rate.medium", 6.50);
        }
        return 0.0;
    }

    public static double getOverdraftLimit(String accountType) {
        if (accountType == null) return 0.0;
        if ("CURRENT".equalsIgnoreCase(accountType)) {
            return currentLoader.getDouble("overdraft.min.limit", 25000.0);
        }
        return 0.0;
    }

    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        double minLimit = currentLoader.getDouble("overdraft.min.limit", 25000.0);
        double multiplier = currentLoader.getDouble("overdraft.multiplier", 2.5);
        return Math.max(minLimit, monthlyTurnover * multiplier);
    }

    public static double getFDInterestRate(int months) {
        if (months >= 36) return fdLoader.getDouble("interest.rate.long", 7.50);
        if (months >= 12) return fdLoader.getDouble("interest.rate.medium", 6.50);
        return fdLoader.getDouble("interest.rate.short", 5.00);
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
