package ru.job4j.convertlist;

import java.util.ArrayList;
import java.util.List;

/**
 * 3. Конвертация двумерного массива в ArrayList и наоборот [#10035].
 Каретко Виктор,  28.07.17 15:27
 Вам необходимо создать класс ConvertList. Внутри методов использовать foreach.

 В нём написать 2 метода:

 public List<Integer> toList (int[][] array) {} - в метод приходит двумерный массив целых чисел, необходимо пройтись по всем элементам массива и добавить их в List<Integer>.

 public int[][] toArray (List<Integer> list, int rows) {} - метод toArray должен равномерно разбить лист на количество строк двумерного массива. В методе toArray должна быть проверка - если количество элементов не кратно количеству строк - оставшиеся значения в массиве заполнять нулями.

 Например в результате конвертации листа со значениями (1,2,3,4,5,6,7) с разбиением на 3 строки должен получиться двумерный массив {{1, 2, 3} {4, 5, 6} {7, 0 ,0}}

 */

/**
 * .
 */
public class ConvertList {

    /**
     * .
     * @param array array.
     * @return list.
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int[] ints: array) {
            for (int i: ints) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * .
     * @param list list.
     * @param rows rows.
     * @return array.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int[][] ints = new int[rows][];
        int columns;

        if (list.size() % rows == 0) {
            columns = list.size() / rows;
        } else {
            columns = (list.size() / rows) + 1;
        }

        int column = 0;
        int row = 0;
        ints[row] = new int[columns];

        for (Integer item: list) {
            if (column < columns) {
                ints[row][column++] = item;
            } else {
                column = 0;
                ints[++row] = new int[columns];
                ints[row][column++] = item;
            }
        }

        while (column < columns) {
            ints[row][column++] = 0;
        }

        return ints;

    }

}
