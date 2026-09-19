package com.gdb.domain;

import com.gdb.exceptions.*;

public class SavingsAccount extends AbstractAccount {
    private double minBalance;
    private double interestRate;

    public SavingsAccount(String accountNumber, String name, int age, double balance, String status, String pin) {
        super(accountNumber, name, age, balance, "SAVINGS", status, pin);
        this.minBalance = AccountRulesEngine.getMinimumBalance("SAVINGS");
        this.interestRate = AccountRulesEngine.getInterestRate("SAVINGS");
    }

    public SavingsAccount(String accountNumber, String name, int age, double balance, String status, String pin, double minBalance, double interestRate) {
        super(accountNumber, name, age, balance, "SAVINGS", status, pin);
        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }

    @Override
    public void processDebit(double amount) throws AccountException {
        AccountRulesEngine.validateWithdrawal("SAVINGS", this.balance, amount, this.minBalance);
        this.balance -= amount;
    }

    public void applyInterest() {
        double interest = this.balance * (interestRate / 100.0);
        this.balance += interest;
    }

    public double getMinBalance() { return minBalance; }
    public double getInterestRate() { return interestRate; }
}
