package section2;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {
    private Map<Integer, Account> accounts = new HashMap<>();

    @Override
    public void save(Account account) {
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Saving account to database");
    }

    @Override
    public Account findById(int accountNumber) {
        return accounts.get(accountNumber);
    }
}
