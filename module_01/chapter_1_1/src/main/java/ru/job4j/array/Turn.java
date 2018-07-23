package ru.job4j.array;

/**
 * Turn.
 */
public class Turn {
    /**
     *
     * @param array исходный массив.
     * @return перевернутый массив.
     */
    public int[] back(int[] array) {
        int length = array.length,
            element;
        for (int i = 0; i < length / 2; i++) {
            element = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = element;
        }
        return array;
    }
}
