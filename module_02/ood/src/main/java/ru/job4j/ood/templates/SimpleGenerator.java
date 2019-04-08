package ru.job4j.ood.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleGenerator implements Template {
    private final static Logger LOGGER = LogManager.getLogger(SimpleGenerator.class);

    @Override
    public String generate(String template, Map<String, String> data) throws KeyTemplateException {
        LOGGER.traceEntry();
        StringBuilder result = new StringBuilder();
        Set<String> keys = new HashSet<>();
        boolean isKey = false;
        int start = 0;
        for (int i = 0; i < template.length(); i++) {
            if (!isKey && template.charAt(i) == '$' && i + 1 < template.length() && template.charAt(i + 1) == '{') {
                isKey = true;
                result.append(template.substring(start, i));
                start = i;
                i += 2;
            } else if (isKey && template.charAt(i) == '}') {
                isKey = false;
                String key = template.substring(start + 2, i);
                String word = data.get(key);
                if (word != null) {
                    keys.add(key);
                    result.append(word);
                    start = i + 1;
                } else {
                    throw new KeyTemplateException("Key not found.");
                }
            }
        }
        if (data.size() != keys.size()) {
            throw new KeyTemplateException("Extra key");
        }
        if (start < template.length()) {
            result.append(template.substring(start));
        }
        return result.toString();
    }
}
