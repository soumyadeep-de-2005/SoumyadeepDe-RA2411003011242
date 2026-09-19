package section3;

/**
 * FixedDepositAccount extends Account.
 * 
 * Notice: FixedDepositAccount DOES NOT implement Withdrawable.
 * In the bad design, it overrode withdraw() to throw UnsupportedOperationException,
 * which crashed automated tests expecting substitutability (LSP violation).
 */
public class FixedDepositAccount extends Account {

    public FixedDepositAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }

    /**
     * Bad design override: throws an exception because FDs cannot be withdrawn early.
     * In the corrected LSP design, calling code should depend on Withdrawable instead.
     */
    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawal is not supported on Fixed Deposit accounts!");
    }
}
