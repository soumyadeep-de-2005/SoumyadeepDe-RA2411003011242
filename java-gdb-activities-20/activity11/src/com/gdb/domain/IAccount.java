package com.gdb.domain;

import com.gdb.exceptions.*;

// TODO: Step 1 - Declare the contract every bank account must follow (method signatures only, no bodies):
//   - Getters for account number, customer name, balance, account type and status
//     (you may also expose getAge(), validatePin(String) and changePin(String, String)).
//   - deposit(double amount), declaring InvalidAmountException.
//   - withdraw(double amount, String enteredPin), declaring AccountException.
//   - displayAccountInfo(), returning void.
//   Tip: every signature must match the method that already exists in AbstractAccount.
public interface IAccount {
    String getAccountNumber();
    String getName();
    int getAge();
    double getBalance();
    String getAccountType();
    String getStatus();
    boolean validatePin(String enteredPin);
    boolean changePin(String oldPin, String newPin);
    void deposit(double amount) throws InvalidAmountException;
    void withdraw(double amount, String enteredPin) throws AccountException;
    void displayAccountInfo();
}
