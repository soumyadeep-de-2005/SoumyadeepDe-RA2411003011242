package section2;

public class SavingsInterestPolicy implements InterestPolicy {
    @Override
    public double calculate(double balance) {
        return balance * 0.04;
    }
}
