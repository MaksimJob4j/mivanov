package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class InputStub implements Input {
    private final static Logger LOGGER = LogManager.getLogger(InputStub.class);
    private final Iterator<String> iterator;
    private final Output output;

    public InputStub(Iterator<String> iterator, Output output) {
        this.iterator = iterator;
        this.output = output;
    }

    @Override
    public String next() {
        LOGGER.traceEntry();
        return this.iterator.next();
    }

    @Override
    public String askString(String question) {
        LOGGER.traceEntry();
        this.output.print(question);
        return this.iterator.next();
    }

    @Override
    public double askDouble(String question) throws ParseException {
        LOGGER.traceEntry();
        String s = this.askString(question);
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Ошибка преобразования '%s' в число", s));
        }
    }

    @Override
    public void close() {

    }
}
