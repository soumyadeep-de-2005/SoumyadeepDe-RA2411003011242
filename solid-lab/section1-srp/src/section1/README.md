# Section 1: Single Responsibility Principle (SRP)

## Task 1: Four Distinct Reasons BankAccount Would Need to Change
1. **Database / Persistence Changes**: If the database changes (e.g. from JDBC to Hibernate or file storage), saving logic has to be modified.
2. **Notification / Email Changes**: If the email vendor changes or notification templates change, email-sending code has to be updated.
3. **Statement Formatting Changes**: If the bank changes how account statements look (layout, headers, formatting), statement generation code has to be altered.
4. **Account Transaction Rules**: If withdrawal rules, minimum balances, or deposit validation change, the core account methods have to be modified.

---

## Task 2: Job Description for BankAccount
> "BankAccount is responsible only for managing account state and performing core account operations: deposit, withdraw, and get balance."

---

## Task 5: Wrap-up Explanation
Separating responsibilities produced 4 classes: `BankAccount`, `AccountRepository`, `NotificationService`, and `StatementGenerator`. This separation makes testing much easier because `BankAccount` can be unit tested quickly in memory without needing to set up a database connection or email server. It also makes `BankAccount` easier to maintain because modifying statement layouts or notification mechanisms will never accidentally break financial calculations or account balances.

---

## Checkpoint
`BankAccount` now contains only `deposit()`, `withdraw()`, simple getters, and account-related state. No database, email, or statement logic remains in `BankAccount`.
