package ru.job4j.ood.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGeneratorRegEx implements Template {
    private final static Logger LOGGER = LogManager.getLogger(SimpleGeneratorRegEx.class);
    private final static Pattern KEY_PATTERN = Pattern.compile("[$][{].*?[}]");

    @Override
    public String generate(String template, Map<String, String> data) throws KeyTemplateException {
        LOGGER.traceEntry();
        Set<String> keys = new HashSet<>();
        boolean find = true;
        while (find) {
            Matcher m = KEY_PATTERN.matcher(template);
            if (m.find()) {
                String key = m.group().substring(2, m.group().length() - 1);
                String replacement = data.get(key);
                if (replacement == null) {
                    throw new KeyTemplateException("Key not found.");
                } else {
                    keys.add(key);
                    template = template.replaceAll("[$][{]" + key + "[}]", replacement);
                }
            } else {
                find = false;
            }
        }
        if (data.size() != keys.size()) {
            throw new KeyTemplateException("Extra key");
        }
        return template;
    }
}