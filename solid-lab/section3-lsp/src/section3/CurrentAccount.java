package section3;

public class CurrentAccount extends Account implements Withdrawable {
    public CurrentAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }
}
