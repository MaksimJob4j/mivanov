package ru.job4j.loop;

/**
 * Board.
 */
public class Board {
    /**
     *
     * @param width ширина.
     * @param height высота.
     * @return шахматная доска.
     */
    public String paint(int width, int height) {
        StringBuilder stringBuilder = new StringBuilder();
        String result;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i + j) % 2 == 0) {
                    stringBuilder.append("X");
                } else {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }
}
