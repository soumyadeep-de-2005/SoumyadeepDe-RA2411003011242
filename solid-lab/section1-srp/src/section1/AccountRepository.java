package section1;

/**
 * Responsible only for persisting account data to storage (SRP).
 */
public class AccountRepository {

    public void save(BankAccount account) {
        System.out.println("Saving account to database");
    }
}
