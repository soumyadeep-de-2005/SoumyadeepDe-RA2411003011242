package section4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FileAccountRepository stores account records in a plain text file.
 * Format per line: accountNumber,name,balance
 */
public class FileAccountRepository implements AccountRepository {
    private String filename;

    public FileAccountRepository(String filename) {
        this.filename = filename;
    }

    public FileAccountRepository() {
        this("accounts.txt");
    }

    @Override
    public void save(Account account) {
        Map<Integer, String> records = readAll();
        records.put(account.getAccountNumber(), account.getAccountNumber() + "," + account.getName() + "," + account.getBalance());

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String line : records.values()) {
                writer.println(line);
            }
            System.out.println("Saving account to file (" + filename + ")");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    @Override
    public Account findById(int accountNumber) {
        Map<Integer, String> records = readAll();
        String line = records.get(accountNumber);
        if (line != null) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double balance = Double.parseDouble(parts[2].trim());
                return new Account(id, name, balance);
            }
        }
        return null;
    }

    private Map<Integer, String> readAll() {
        Map<Integer, String> map = new LinkedHashMap<>();
        File file = new File(filename);
        if (!file.exists()) {
            return map;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 1) {
                        int id = Integer.parseInt(parts[0].trim());
                        map.put(id, line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return map;
    }
}
