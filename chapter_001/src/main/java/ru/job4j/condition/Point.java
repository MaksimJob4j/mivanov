package ru.job4j.condition;

/**
 * Point.
 */
public class Point {
    /**
     * x.
     */
    private int x;
    /**
     * y.
     */
    private int y;

    /**
     *
     * @param x x
     * @param y y
     */
    public  Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     *
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     *
     * @param a a
     * @param b b
     * @return is point on line.
     */
    public boolean isPointOnLine(int a, int b) {
        return getY() == a * getX() + b ? true : false;
    }
}
