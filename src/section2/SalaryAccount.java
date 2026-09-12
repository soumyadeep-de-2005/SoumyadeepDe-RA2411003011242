package section2;

/**
 * SalaryAccount extends Account.
 * Added without modifying any existing account or policy classes (OCP).
 */
public class SalaryAccount extends Account {
    public SalaryAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }
}
