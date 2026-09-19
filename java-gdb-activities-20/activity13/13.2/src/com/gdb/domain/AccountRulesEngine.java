package com.gdb.domain;

import com.gdb.exceptions.*;
import java.util.HashMap;
import java.util.Map;

public class AccountRulesEngine {
    private static AccountRulesEngine instance;

    public static class Rule {
        private final double minBalance;
        private final double interestRate;
        private final double overdraftLimit;
        private final String featureName;

        public Rule(double minBalance, double interestRate, double overdraftLimit, String featureName) {
            this.minBalance = minBalance;
            this.interestRate = interestRate;
            this.overdraftLimit = overdraftLimit;
            this.featureName = featureName;
        }

        public double getMinBalance() { return minBalance; }
        public double getInterestRate() { return interestRate; }
        public double getOverdraftLimit() { return overdraftLimit; }
        public String getFeatureName() { return featureName; }
    }

    // Nested lookup structure: Account Type -> Category / Bucket -> Rule
    private static final Map<String, Map<String, Rule>> RULES_MAP = new HashMap<>();

    static {
        loadRules();
    }

    public static synchronized AccountRulesEngine getInstance() {
        if (instance == null) {
            instance = new AccountRulesEngine();
        }
        return instance;
    }

    public static void loadRules() {
        RULES_MAP.clear();

        // SAVINGS rules
        Map<String, Rule> savingsRules = new HashMap<>();
        savingsRules.put("NEW", new Rule(10000.0, 2.70, 0.0, "New Customer (0-1 yr)"));
        savingsRules.put("STANDARD", new Rule(7500.0, 3.00, 0.0, "Standard Customer (1-3 yrs)"));
        savingsRules.put("PREMIUM", new Rule(5000.0, 3.50, 0.0, "Premium Customer (3-5 yrs)"));
        savingsRules.put("PRIVILEGE", new Rule(2500.0, 4.00, 0.0, "Privilege Customer (5+ yrs)"));
        savingsRules.put("DEFAULT", new Rule(1000.0, 4.00, 0.0, "Default Savings"));
        RULES_MAP.put("SAVINGS", savingsRules);

        // CURRENT rules
        Map<String, Rule> currentRules = new HashMap<>();
        currentRules.put("DEFAULT", new Rule(0.0, 0.0, 25000.0, "Standard Overdraft"));
        RULES_MAP.put("CURRENT", currentRules);

        // FIXED_DEPOSIT rules
        Map<String, Rule> fdRules = new HashMap<>();
        fdRules.put("SHORT", new Rule(0.0, 5.00, 0.0, "FD < 12 months"));
        fdRules.put("MEDIUM", new Rule(0.0, 6.50, 0.0, "FD 12-35 months"));
        fdRules.put("LONG", new Rule(0.0, 7.50, 0.0, "FD 36+ months"));
        fdRules.put("DEFAULT", new Rule(0.0, 6.50, 0.0, "Default Fixed Deposit"));
        RULES_MAP.put("FIXED_DEPOSIT", fdRules);

        // SALARY rules
        Map<String, Rule> salaryRules = new HashMap<>();
        salaryRules.put("DEFAULT", new Rule(0.0, 0.0, 0.0, "Salary Account"));
        RULES_MAP.put("SALARY", salaryRules);
    }

    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) return "PRIVILEGE";
        if (tenureYears >= 3) return "PREMIUM";
        if (tenureYears >= 1) return "STANDARD";
        return "NEW";
    }

    public static double getSavingsMinBalance(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        Map<String, Rule> savings = RULES_MAP.get("SAVINGS");
        if (savings != null && savings.containsKey(bucket)) {
            return savings.get(bucket).getMinBalance();
        }
        return 10000.0;
    }

    public static double getSavingsInterestRate(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        Map<String, Rule> savings = RULES_MAP.get("SAVINGS");
        if (savings != null && savings.containsKey(bucket)) {
            return savings.get(bucket).getInterestRate();
        }
        return 2.70;
    }

    public static double getMinimumBalance(String accountType) {
        if (accountType == null) return 0.0;
        Map<String, Rule> rules = RULES_MAP.get(accountType.toUpperCase());
        if (rules != null && rules.containsKey("DEFAULT")) {
            return rules.get("DEFAULT").getMinBalance();
        }
        return 0.0;
    }

    public static double getInterestRate(String accountType) {
        if (accountType == null) return 0.0;
        Map<String, Rule> rules = RULES_MAP.get(accountType.toUpperCase());
        if (rules != null && rules.containsKey("DEFAULT")) {
            return rules.get("DEFAULT").getInterestRate();
        }
        return 0.0;
    }

    public static double getOverdraftLimit(String accountType) {
        if (accountType == null) return 0.0;
        Map<String, Rule> rules = RULES_MAP.get(accountType.toUpperCase());
        if (rules != null && rules.containsKey("DEFAULT")) {
            return rules.get("DEFAULT").getOverdraftLimit();
        }
        return 0.0;
    }

    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        return Math.max(25000.0, monthlyTurnover * 2.5);
    }

    public static double getFDInterestRate(int months) {
        Map<String, Rule> fdRules = RULES_MAP.get("FIXED_DEPOSIT");
        if (fdRules != null) {
            if (months >= 36 && fdRules.containsKey("LONG")) {
                return fdRules.get("LONG").getInterestRate();
            }
            if (months >= 12 && fdRules.containsKey("MEDIUM")) {
                return fdRules.get("MEDIUM").getInterestRate();
            }
            if (fdRules.containsKey("SHORT")) {
                return fdRules.get("SHORT").getInterestRate();
            }
        }
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
