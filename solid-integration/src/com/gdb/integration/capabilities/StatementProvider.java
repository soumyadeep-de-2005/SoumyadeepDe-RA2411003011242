package com.gdb.integration.capabilities;

/**
 * Interface Segregation Principle (ISP) capability interface.
 * Represents entities capable of rendering account statements and status.
 */
public interface StatementProvider {
    void displayAccountInfo();
}
