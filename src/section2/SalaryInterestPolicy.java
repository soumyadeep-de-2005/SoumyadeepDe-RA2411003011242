package section2;

public class SalaryInterestPolicy implements InterestPolicy {
    @Override
    public double calculate(double balance) {
        return balance * 0.05;
    }
}
