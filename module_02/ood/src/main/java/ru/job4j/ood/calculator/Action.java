package ru.job4j.ood.calculator;

public interface Action {
    String operation();
    double calculate(double first, double second) throws CalculateException;

}
