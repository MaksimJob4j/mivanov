package ru.job4j.ood.templates;

import java.util.Map;

public interface Template {
    String generate(String template, Map<String, String> data) throws KeyTemplateException;
}
