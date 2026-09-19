package section4;

/**
 * DIP Abstraction:
 * Bank depends on this interface rather than a concrete repository class.
 */
public interface AccountRepository {
    void save(Account account);
    Account findById(int accountNumber);
}
