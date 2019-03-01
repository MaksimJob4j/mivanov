package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class InputConsole implements Input {
    private final static Logger LOGGER = LogManager.getLogger(InputConsole.class);
    private final Output output;
    private final Scanner console = new Scanner(System.in);

    public InputConsole(Output output) {
        this.output = output;
    }

    @Override
    public String next() {
        LOGGER.traceEntry();
        return this.console.nextLine();
    }

    @Override
    public String askString(String question) {
        LOGGER.traceEntry();
        this.output.print(question);
        return this.console.nextLine();
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
        LOGGER.traceEntry();
    }
}
