package com.gdb.integration.capabilities;

/**
 * Interface Segregation Principle (ISP) capability interface.
 * Represents entities eligible to apply for institutional credit lines.
 */
public interface LoanEligible {
    boolean checkLoanEligibility();
    void applyForLoan(double amount);
}
