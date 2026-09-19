package com.gdb.integration.capabilities;

import com.gdb.exceptions.AccountException;

/**
 * Interface Segregation Principle (ISP) capability interface.
 * Represents entities that support peer-to-peer funds transfers.
 */
public interface Transferable {
    void transfer(double amount, String pin, String targetAccountNumber) throws AccountException;
}
