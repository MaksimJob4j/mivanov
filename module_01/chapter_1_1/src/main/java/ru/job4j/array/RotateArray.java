package ru.job4j.array;

/**
 * RotateArray.
 */
public class RotateArray {
    /**
     *
     * @param array двумерный массив.
     * @return повернутый по часовой стрелке массив.
     */
    public int[][] rotate(int[][] array) {
        int     length = array[0].length,
                element;
        for (int i = 0; i < length / 2; i++) {
            for (int j = i; j < length - i - 1; j++) {
                element = array [i][j];
                array[i][j] = array[length - j - 1][i];
                array[length - j - 1][i] = array[length - i - 1][length - j - 1];
                array[length - i - 1][length - j - 1] = array[j][length - i - 1];
                array[j][length - i - 1] = element;
            }
        }
        return array;
    }
}
