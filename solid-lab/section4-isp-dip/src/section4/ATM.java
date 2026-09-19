package section4;

/**
 * ISP Solution:
 * ATM implements only Depositable and Withdrawable.
 */
public class ATM implements Depositable, Withdrawable {
    private String atmId;
    private double cashOnHand;

    public ATM(String atmId, double cashOnHand) {
        this.atmId = atmId;
        this.cashOnHand = cashOnHand;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            cashOnHand += amount;
            System.out.println("ATM [" + atmId + "] accepted cash deposit of $" + amount);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
        } else if (amount > cashOnHand) {
            System.out.println("ATM [" + atmId + "] insufficient cash in dispenser.");
        } else {
            cashOnHand -= amount;
            System.out.println("ATM [" + atmId + "] dispensed cash $" + amount);
        }
    }
}
