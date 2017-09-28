package ru.job4j.chess;

/**
 * Cell.
 */
public class Cell {
    // Индексы считаются как в массиве - с верхнего левого угла
    /**
     * indexX.
     */
    private int indexX;
    /**
     * IndexY.
     */
    private int indexY;
    /**
     * index.
     */
    private int index;

    /**
     *
     * @return getIndexX.
     */
    public int getIndexX() {
        return indexX;
    }

    /**
     *
     * @return getIndexY.
     */
    public int getIndexY() {
        return indexY;
    }

    /**
     *
     * @return index.
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @param indexX indexX.
     * @param indexY indexY.
     */
    public Cell(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.index = indexX + indexY * 8;
    }



}
