package com.gdb.integration.capabilities;

import com.gdb.exceptions.AccountException;

/**
 * Interface Segregation Principle (ISP) capability interface.
 * Represents entities that support PIN-authenticated withdrawals.
 */
public interface Withdrawable {
    void withdraw(double amount, String pin) throws AccountException;
}
