package ru.job4j.inout.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private final static Logger LOGGER = LogManager.getLogger(Config.class);

    private final String path;

    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void load() {
        LOGGER.traceEntry();
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach((s) -> {
                if (!s.matches("(^[ ]*[#].*)|([^=]*)")) {
                    String key = s.split("=")[0];
                    this.values.put(
                            key.trim(),
                            s.substring(key.length() + 1).trim());
                }
            });
        } catch (Exception e) {
            LOGGER.traceEntry(e.getMessage(), e);
        }
    }

    public String value(String key) throws UnexpectedPropertyException {
        LOGGER.traceEntry();
        String value = values.get(key);
        if (value != null) {
            return value;
        }
        throw new UnexpectedPropertyException(String.format("Unexpected property %s", key));
    }

    @Override
    public String toString() {
        LOGGER.traceEntry();
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            LOGGER.traceEntry(e.getMessage(), e);
        }
        return out.toString();
    }

    public static void main(String[] args) throws UnexpectedPropertyException {
        Config config = new Config("app.properties");
        System.out.println(config);
        config.load();
        System.out.println(config.value("hibernate.dialect"));
    }
}
