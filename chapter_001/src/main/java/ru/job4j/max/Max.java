package ru.job4j.max;

/**
 *  Max.
 */
public class Max {
    /**
     *
     * @param first first
     * @param second second
     * @return max
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    };
    /**
     *
     * @param first first
     * @param second second
     * @param third third
     * @return max
     */
    public int max(int first, int second, int third) {
        return max(first, max(second, third));
    };
}
