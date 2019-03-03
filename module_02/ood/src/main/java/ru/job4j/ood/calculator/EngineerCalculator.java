package ru.job4j.ood.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class EngineerCalculator extends Calculator {
    private final static Logger LOGGER = LogManager.getLogger(EngineerCalculator.class);
    private final Set<String> unaryOperations = new HashSet<>();

    public Set<String> getUnaryOperations() {
        LOGGER.traceEntry();
        return unaryOperations;
    }

    public boolean isUnary(String operation) {
        LOGGER.traceEntry();
        return this.unaryOperations.contains(operation.trim().toLowerCase());
    }

    @Override
    public void init() {
        LOGGER.traceEntry();
        super.init();
        addUnaryAction(new Sin());
        addUnaryAction(new Cos());
    }

    public void addUnaryAction(Action action) {
        LOGGER.traceEntry();
        addAction(action);
        this.unaryOperations.add(action.operation());
    }

    private class Sin implements Action {

        @Override
        public String operation() {
            return "sin";
        }

        @Override
        public double calculate(double first, double second) {
            return Math.sin(first);
        }
    }

    private class Cos implements Action {

        @Override
        public String operation() {
            return "cos";
        }

        @Override
        public double calculate(double first, double second) {
            return Math.cos(first);
        }
    }

}
