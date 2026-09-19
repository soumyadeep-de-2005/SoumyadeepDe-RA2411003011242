package section3;

/**
 * Capability contract for accounts that genuinely support withdrawal operations (LSP).
 */
public interface Withdrawable {
    void withdraw(double amount);
}
