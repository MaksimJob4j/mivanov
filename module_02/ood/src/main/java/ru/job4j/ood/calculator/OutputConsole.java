package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OutputConsole implements Output {
    private final static Logger LOGGER = LogManager.getLogger(OutputConsole.class);

    @Override
    public void print(String string) {
        LOGGER.traceEntry();
        System.out.println(string);
    }

    @Override
    public void close() {
        LOGGER.traceEntry();
    }
}
