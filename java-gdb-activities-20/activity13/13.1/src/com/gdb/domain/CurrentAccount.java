package com.gdb.domain;

import com.gdb.exceptions.*;

public class CurrentAccount extends AbstractAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String name, int age, double balance, String status, String pin, double overdraftLimit) {
        super(accountNumber, name, age, balance, "CURRENT", status, pin);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void processDebit(double amount) throws AccountException {
        AccountRulesEngine.validateWithdrawal("CURRENT", this.balance, amount, this.overdraftLimit);
        this.balance -= amount;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }
}
