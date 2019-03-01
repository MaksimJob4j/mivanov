package ru.job4j.ood.calculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InteractCalculatorTest {

    @Test
    public void whenOneOperationThenGetResult() {
        List<String> in = Arrays.asList(
                "2",
                "+",
                "5",
                "",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Введите операцию",
                "Введите второе число",
                "Результат: 7.0",
                "Сохранить результат? (y)",
                "Выход? (y)");
        testThem(in, out);
    }

    @Test
    public void whenTwoOperationsThenGetResult() {
        List<String> in = Arrays.asList(
                "2",
                "+",
                "5",
                "",
                "",
                "2",
                "*",
                "5",
                "",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Введите операцию",
                "Введите второе число",
                "Результат: 7.0",
                "Сохранить результат? (y)",
                "Выход? (y)",
                "Введите первое число",
                "Введите операцию",
                "Введите второе число",
                "Результат: 10.0",
                "Сохранить результат? (y)",
                "Выход? (y)");
        testThem(in, out);
    }

    @Test
    public void whenLongOperationThenGetResult() {
        List<String> in = Arrays.asList(
                "7",
                "-",
                "1",
                "y",
                "/",
                "3",
                "",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Введите операцию",
                "Введите второе число",
                "Результат: 6.0",
                "Сохранить результат? (y)",
                "Введите операцию",
                "Введите второе число",
                "Результат: 2.0",
                "Сохранить результат? (y)",
                "Выход? (y)");
        testThem(in, out);
    }

    @Test
    public void whenDivByZeroThenWarning() {
        List<String> in = Arrays.asList(
                "2",
                "/",
                "0",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Введите операцию",
                "Введите второе число",
                "Деление на ноль",
                "Выход? (y)");
        testThem(in, out);
    }

    @Test
    public void whenFirstNotNumberThenWarning() {
        List<String> in = Arrays.asList(
                "q",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Ошибка преобразования 'q' в число",
                "Выход? (y)");
        testThem(in, out);
    }

    @Test
    public void whenSecondNotNumberThenWarning() {
        List<String> in = Arrays.asList(
                "1",
                "+",
                "q",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Введите операцию",
                "Введите второе число",
                "Ошибка преобразования 'q' в число",
                "Выход? (y)");
        testThem(in, out);
    }

    @Test
    public void whenOperationNotDefinedThenWarning() {
        List<String> in = Arrays.asList(
                "1",
                "sin",
                "y");
        List<String> out = Arrays.asList(
                "Введите первое число",
                "Введите операцию",
                "Операция не поддерживается",
                "Выход? (y)");
        testThem(in, out);
    }

    private void testThem(List<String> in, List<String> out) {
        Calculator calc = new Calculator();
        OutputStub output = new OutputStub();
        Input input = new InputStub(in.iterator(), output);
        InteractCalculator iCalc = new InteractCalculator(calc, input, output);
        iCalc.init();
        iCalc.start();
        assertThat(output.getStrings(), is(out));
    }

}