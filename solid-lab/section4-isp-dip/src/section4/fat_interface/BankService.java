package section4.fat_interface;

/**
 * Fat interface violating ISP.
 */
public interface BankService {
    void deposit(double amount);
    void withdraw(double amount);
    void transfer(double amount, String toAccount);
    void printStatement();
    void applyForLoan();
}
