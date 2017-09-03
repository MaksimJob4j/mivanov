package ru.job4j.array;

/**
 * BubbleSort.
 */
public class BubbleSort {
    /**
     *
     * @param array массив.
     * @return отсортированный методом пузырьковой сортировки массив.
     */
    public int[] sort(int[] array) {
        int length = array.length,
            element;

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    element = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = element;
                }
            }
        }
        return array;
    }
}
