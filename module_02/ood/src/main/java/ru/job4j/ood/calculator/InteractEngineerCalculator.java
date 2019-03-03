package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class InteractEngineerCalculator {
    private final static Logger LOGGER = LogManager.getLogger(InteractEngineerCalculator.class);
    private final EngineerCalculator calculator;
    private final Input input;
    private final Output output;
    private Set<String> operations;

    public InteractEngineerCalculator(EngineerCalculator calculator, Input input, Output output) {
        this.calculator = calculator;
        this.input = input;
        this.output = output;
    }

    public void init() {
        LOGGER.traceEntry();
        this.calculator.init();
        this.operations = this.calculator.operations();
    }

    public void start() {
        LOGGER.traceEntry();
        boolean longCalc = false;
        double result = 0;
        do {
            try {
                double first;
                if (longCalc) {
                    first = result;
                } else {
                    first = this.input.askDouble("Введите первое число");
                }
                String op = this.input.askString("Введите операцию");
                if (this.operations.contains(op)) {
                    double second = 0;
                    if (!calculator.isUnary(op)) {
                        second = this.input.askDouble("Введите второе число");
                    }
                    result = this.calculator.calculate(first, second, op);
                    this.output.print(String.format("Результат: %s", result));
                    longCalc = "y".equals(this.input.askString("Сохранить результат? (y)").trim().toLowerCase());
                } else {
                    this.output.print("Операция не поддерживается");
                }
            } catch (ParseException | CalculateException e) {
                this.output.print(e.getMessage());
            }

        } while (longCalc || !"y".equals(this.input.askString("Выход? (y)").trim().toLowerCase()));
    }

    public static void main(String[] args) {
        Output output = new OutputConsole();
        InteractEngineerCalculator iCalc = new InteractEngineerCalculator(
                new EngineerCalculator(), new InputConsole(output), output
        );
        iCalc.init();
        iCalc.start();
    }
}
