package ru.job4j.range;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RangeCounting {
    private List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(func.apply((double) i));
        }
        return result;

    }

    List<Double> diapasonLine(int start, int end) {
        return diapason(start, end, (n) -> n);
    }

    List<Double> diapasonQuadratic(int start, int end) {
        return diapason(start, end, (n) -> Math.pow(n, 2));
    }

    List<Double> diapasonLogarithmic(int start, int end) {
        return diapason(start, end, Math::log);
    }
}
