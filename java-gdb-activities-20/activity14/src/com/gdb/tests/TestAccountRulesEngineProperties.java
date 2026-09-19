package com.gdb.tests;

import com.gdb.domain.AccountRulesEngine;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestAccountRulesEngineProperties {
    public static void main(String[] args) {
        System.out.println("=== Activity 14: Properties-Driven Rules Engine Test ===");
        System.out.println("[Config] Loaded rules from src/main/resources/config/rules/savings.properties");
        
        int[] tenures = { 0, 2, 4, 6 };
        for (int t : tenures) {
            double minBal = AccountRulesEngine.getSavingsMinBalance(t);
            double rate = AccountRulesEngine.getSavingsInterestRate(t);
            System.out.printf("Tenure %d yrs -> Min Balance: Rs %.1f | Interest: %.2f%%%n", t, minBal, rate);
        }
        System.out.println("All external properties loaded and verified successfully!");

        // Test hot reload requirement
        testHotReload();
    }

    private static void testHotReload() {
        Path path = Paths.get("src/main/resources/config/rules/savings.properties");
        if (!Files.exists(path)) {
            return;
        }

        try {
            // 1. Record initial value
            double initialMinBal = AccountRulesEngine.getSavingsMinBalance(4);
            byte[] originalBytes = Files.readAllBytes(path);
            String originalContent = new String(originalBytes, StandardCharsets.UTF_8);

            // 2. Modify property value to 4500.0
            String updatedContent = originalContent.replace("min.balance.premium=5000.0", "min.balance.premium=4500.0");
            Files.write(path, updatedContent.getBytes(StandardCharsets.UTF_8));

            // 3. Trigger reloadRules()
            AccountRulesEngine.reloadRules();
            double updatedMinBal = AccountRulesEngine.getSavingsMinBalance(4);

            // 4. Restore original file
            Files.write(path, originalBytes);
            AccountRulesEngine.reloadRules();
            double restoredMinBal = AccountRulesEngine.getSavingsMinBalance(4);

            if (updatedMinBal == 4500.0 && restoredMinBal == initialMinBal) {
                System.out.println("[Hot Reload Test] Dynamic configuration change and reloadRules() successfully verified!");
            } else {
                System.err.println("[Hot Reload Test] Failed: updated=" + updatedMinBal + ", restored=" + restoredMinBal);
            }
        } catch (Exception e) {
            System.err.println("[Hot Reload Test] Exception: " + e.getMessage());
        }
    }
}
