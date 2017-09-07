package ru.job4j.array;

/**
 * Тестовое задание. Часть 001. Базовый синтаксис.
 * Класс слияния отсортированных массивов.
 */
public class ArraysMerge {

    /**
     *
     * @param firstArray отсортированный массив.
     * @param secondArray массив, отсортированный в том же направлении что и первый.
     * @return Массив, образованный слиянием.
     */
    public int[] merge(int[] firstArray, int[] secondArray) {
        int     firstLength = firstArray.length,
                secondLength = secondArray.length,
                mergeLength = firstLength + secondLength,
                firstIndex = 0,
                secondIndex = 0;
        int[] mergedArray = new int[mergeLength];
        boolean isIncrease;

        if (firstArray[0] < firstArray[firstLength - 1]
                || secondArray[0] < secondArray[secondLength - 1]) {
            isIncrease = true;
        } else {
            isIncrease = false;
        }

        for (int i = 0; i < mergeLength; i++) {
            if (firstIndex == firstLength) {
                mergedArray[i] = secondArray[secondIndex++];
            } else if (secondIndex == secondLength) {
                mergedArray[i] = firstArray[firstIndex++];
            } else if (isIncrease) {
                if (firstArray[firstIndex] < secondArray[secondIndex]) {
                    mergedArray[i] = firstArray[firstIndex++];
                } else {
                    mergedArray[i] = secondArray[secondIndex++];
                }
            } else  if (firstArray[firstIndex] > secondArray[secondIndex]) {
                mergedArray[i] = firstArray[firstIndex++];
            } else {
                mergedArray[i] = secondArray[secondIndex++];
            }

        }
        return mergedArray;
    }
}
