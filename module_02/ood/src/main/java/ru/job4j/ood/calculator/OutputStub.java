package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OutputStub implements Output {
    private final static Logger LOGGER = LogManager.getLogger(OutputStub.class);
    private final List<String> strings = new ArrayList<>();

    @Override
    public void print(String string) {
        LOGGER.traceEntry();
        strings.add(string);
    }

    @Override
    public void close() {
        LOGGER.traceEntry();
    }

    List<String> getStrings() {
        LOGGER.traceEntry();
        return strings;
    }
}
