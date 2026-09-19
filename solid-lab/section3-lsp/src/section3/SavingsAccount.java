package section3;

public class SavingsAccount extends Account implements Withdrawable {
    public SavingsAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }
}
