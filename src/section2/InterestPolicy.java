package section2;

/**
 * Strategy interface for calculating interest (OCP).
 */
public interface InterestPolicy {
    double calculate(double balance);
}
