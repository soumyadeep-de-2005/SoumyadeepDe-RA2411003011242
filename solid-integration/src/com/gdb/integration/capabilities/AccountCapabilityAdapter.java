package com.gdb.integration.capabilities;

import com.gdb.domain.IAccount;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InvalidAmountException;

/**
 * Adapter bridging Global Digital Bank's IAccount domain abstraction
 * into focused, segregated role interfaces (ISP).
 *
 * Client components like an ATM terminal only need Depositable and Withdrawable,
 * while a statement kiosk only requires StatementProvider.
 */
public class AccountCapabilityAdapter implements Depositable, Withdrawable, StatementProvider {
    private final IAccount account;

    public AccountCapabilityAdapter(IAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account reference cannot be null");
        }
        this.account = account;
    }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        account.deposit(amount);
    }

    @Override
    public void withdraw(double amount, String pin) throws AccountException {
        account.withdraw(amount, pin);
    }

    @Override
    public void displayAccountInfo() {
        account.displayAccountInfo();
    }

    public IAccount getAccount() {
        return account;
    }
}
