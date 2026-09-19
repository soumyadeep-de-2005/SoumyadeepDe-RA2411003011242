package section2.legacy;

/**
 * ============================================================================
 * SECTION 2 — OCP: Legacy InterestCalculator with if/else chain
 * ============================================================================
 * 
 * If a 4th account type (e.g., "Salary" or "FixedDeposit") needs to be added:
 * 1. We must open this class and modify the calculate() method directly.
 * 2. We must add another "else if (accountType.equals(...))" branch.
 * 3. We must re-compile and re-test all existing types ("Savings", "Current") to ensure no regressions.
 * This violates the Open/Closed Principle because the class is not closed for modification.
 */
public class InterestCalculator {

    public double calculate(String accountType, double balance) {
        if ("Savings".equalsIgnoreCase(accountType)) {
            return balance * 0.04;
        } else if ("Current".equalsIgnoreCase(accountType)) {
            return balance * 0.01;
        } else {
            return 0.0;
        }
    }
}
