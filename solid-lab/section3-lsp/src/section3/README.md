# Section 3: Liskov Substitution Principle (LSP)

## Task 1: Why Square/Rectangle Violates LSP
Calling code expects a `Rectangle` to have independent dimensions where modifying `width` does not change `height`. `Square` breaks this behavioral contract by forcing `width` and `height` to always match. When code sets `width` to 10 and `height` to 20 on a `Square` referenced as a `Rectangle`, both sides become 20 and the area becomes 400 instead of 200. Because `Square` cannot be substituted for `Rectangle` without altering program correctness, this violates LSP.

---

## Task 4: Why Throwing an Exception is NOT a Correct LSP Solution
Making `FixedDepositAccount` implement `Withdrawable` and throwing an `UnsupportedOperationException` is the wrong fix because of the core substitution rule: any subtype must be safely usable wherever its parent type or interface is expected without causing runtime failures. When an interface defines `withdraw(double amount)`, it establishes a contract that calling this method is supported. Throwing an exception breaks that promise, forcing client code to use `instanceof` checks or `try-catch` blocks. The proper LSP solution is to separate the capability into the `Withdrawable` interface and only implement it on accounts that genuinely support withdrawals.

---

## Checkpoint
No class in the codebase overrides a method just to throw "not supported". Types only implement contracts they can genuinely fulfill.
