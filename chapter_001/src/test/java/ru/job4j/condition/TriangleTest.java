package ru.job4j.condition;

import org.junit.Test;


import static org.junit.Assert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

/**
 * TriangleTest.
 */
public class TriangleTest {
    /**
     * Test.
     */
    @Test
    public void whenPeriodSetThreeLengthsThenPeriod() {
        // создаем три объекта класса Point.
        Point a = new Point(0, 0);
        Point b = new Point(0, 0);
        Point c = new Point(0, 0);
        // Создаем объект треугольник и передаем в него объекты точек.
        Triangle triangle = new Triangle(a, b, c);
        // создаем три отрезка.
        double ab = 3D;
        double ac = 4D;
        double bc = 5D;
        // Вычисляем расстояние между точками.
        double result = triangle.period(ab, ac, bc);
        // Задаем ожидаемый результат.
        double expected = 6D;
        //Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }

    /**
     * Test.
     */
    @Test
    public void whenAreaSetThreePointsThenTriangleArea() {
        // создаем три объекта класса Point.
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Point c = new Point(2, 0);
        // Создаем объект треугольник и передаем в него объекты точек.
        Triangle triangle = new Triangle(a, b, c);
        // Вычисляем расстояние площадь.
        double result = triangle.area();
        // Задаем ожидаемый результат.
        double expected = 2D;
        //Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }

    /**
     * Test.
     */
    @Test
    public void whenAreaSetThreePointsOnLineThenTriangleHasNoArea() {
        // создаем три объекта класса Point.
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Point c = new Point(0, 4);
        // Создаем объект треугольник и передаем в него объекты точек.
        Triangle triangle = new Triangle(a, b, c);
        // Вычисляем расстояние площадь.
        double result = triangle.area();
        // Задаем ожидаемый результат.
        double expected = -1D;
        //Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }

    /**
     * Test.
     */
    @Test
    public void whenDistanceSetTwoPointsThenDistance() {
        // создаем три объекта класса Point.
        Point a = new Point(3, 0);
        Point b = new Point(0, 4);
        Point c = new Point(0, 0);
        // Создаем объект треугольник и передаем в него объекты точек.
        Triangle triangle = new Triangle(a, b, c);
        // Вычисляем площадь.
        double result = triangle.distance(a, b);
        // Задаем ожидаемый результат.
        double expected = 5D;
        //Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }
}
