package com.gdb.integration.capabilities;

import com.gdb.exceptions.InvalidAmountException;

/**
 * Interface Segregation Principle (ISP) capability interface.
 * Represents entities that accept monetary deposits.
 */
public interface Depositable {
    void deposit(double amount) throws InvalidAmountException;
}
