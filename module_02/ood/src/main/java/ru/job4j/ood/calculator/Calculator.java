package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Calculator {
    private final static Logger LOGGER = LogManager.getLogger(Calculator.class);
    private double result;
    private final Map<String, Action> actions = new HashMap<>();

    public void init() {
        LOGGER.traceEntry();
        this.result = 0;
        addAction(new Sum());
        addAction(new Subtract());
        addAction(new Multiply());
        addAction(new Division());
    }

    public void addAction(Action action) {
        LOGGER.traceEntry();
        this.actions.put(action.operation(), action);
    }

    public double calculate(double first, double second, String operation) throws CalculateException {
        LOGGER.traceEntry();
        this.result = actions.get(operation).calculate(first, second);
        return this.result;
    }

    public Set<String> operations() {
        return this.actions.keySet();
    }

    private class Sum implements Action {

        @Override
        public String operation() {
            return "+";
        }

        @Override
        public double calculate(double first, double second) {
            return first + second;
        }
    }

    private class Subtract implements Action {

        @Override
        public String operation() {
            return "-";
        }

        @Override
        public double calculate(double first, double second) {
            return first - second;
        }
    }

    private class Multiply implements Action {

        @Override
        public String operation() {
            return "*";
        }

        @Override
        public double calculate(double first, double second) {
            return first * second;
        }
    }

    private class Division implements Action {

        @Override
        public String operation() {
            return "/";
        }

        @Override
        public double calculate(double first, double second) throws CalculateException {
            if (second == 0) {
                throw new CalculateException("Деление на ноль");
            } else {
                return first / second;
            }
        }
    }
}
