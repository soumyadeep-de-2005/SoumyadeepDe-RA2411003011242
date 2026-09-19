package section4.fat_interface;

/**
 * ATM forced to implement all methods in the fat BankService interface.
 */
public class ATM implements BankService {

    @Override
    public void deposit(double amount) {
        System.out.println("ATM deposit: $" + amount);
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("ATM cash withdrawal: $" + amount);
    }

    // --- METHODS ATM DOES NOT ACTUALLY NEED ---

    // NOT NEEDED: ATM machines do not handle account-to-account wire transfers
    @Override
    public void transfer(double amount, String toAccount) {
        throw new UnsupportedOperationException("ATM does not support wire transfers.");
    }

    // NOT NEEDED: Simple ATM terminals do not print comprehensive account statements
    @Override
    public void printStatement() {
        throw new UnsupportedOperationException("ATM does not print detailed statements.");
    }

    // NOT NEEDED: ATM terminals cannot process or approve loan applications
    @Override
    public void applyForLoan() {
        throw new UnsupportedOperationException("ATM does not support loan applications.");
    }
}
