package com.gdb.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AccountRulesPropertiesLoader {
    private final Properties properties = new Properties();
    private final String configPath;

    public AccountRulesPropertiesLoader(String configPath) {
        this.configPath = configPath;
        loadProperties(configPath);
    }

    public void reload() {
        this.properties.clear();
        loadProperties(this.configPath);
    }

    private void loadProperties(String configPath) {
        InputStream is = null;
        try {
            // 1. Try classpath first
            is = getClass().getClassLoader().getResourceAsStream(configPath);

            // 1.1 If path starts with src/main/resources/, try stripping prefix for classpath
            if (is == null && configPath.startsWith("src/main/resources/")) {
                String subPath = configPath.substring("src/main/resources/".length());
                is = getClass().getClassLoader().getResourceAsStream(subPath);
            }

            // 2. Fall back to FileInputStream if file exists on disk
            if (is == null) {
                File file = new File(configPath);
                if (file.exists()) {
                    is = new FileInputStream(file);
                }
            }

            // 3. Load properties if stream opened
            if (is != null) {
                properties.load(is);
            } else {
                System.err.println("Warning: Config file not found: " + configPath);
            }
        } catch (Exception e) {
            System.err.println("Warning: Error loading configuration from " + configPath + ": " + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public String getProperty(String key, String defaultValue) {
        if (key == null) return defaultValue;
        String val = properties.getProperty(key);
        return (val != null) ? val.trim() : defaultValue;
    }

    public double getDouble(String key, double defaultValue) {
        String val = getProperty(key, null);
        if (val == null || val.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        String val = getProperty(key, null);
        if (val == null || val.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getConfigPath() {
        return configPath;
    }
}
