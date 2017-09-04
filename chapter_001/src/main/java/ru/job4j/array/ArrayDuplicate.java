package ru.job4j.array;

import java.util.Arrays;

/**
 * ArrayDuplicate.
 */
public class ArrayDuplicate {
    /**
     *
     * @param array массив.
     * @return массив без дубликатов.
     */
    public String[] remove(String[] array) {
        int newLength = array.length;
        String currentElement;

        for (int i = 0; i < newLength; i++) {
            currentElement = array[i];
            int j = i + 1;
            while (j < newLength) {
                if (currentElement.equals(array[j])) {
                    newLength--;
                    for (int k = j; k < newLength; k++) {
                        array[k] = array[k + 1];
                        array[k + 1] = currentElement;
                    }
                } else {
                    j++;
                }
            }
        }
        return Arrays.copyOf(array, newLength);
    }
}
