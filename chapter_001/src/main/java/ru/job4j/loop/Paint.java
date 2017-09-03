package ru.job4j.loop;

/**
 * Paint.
 */
public class Paint {
    /**
     *
     * @param h высота.
     * @return пирамиду.
     */
    public String piramid(int h) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < h; i++) {
            if (i > 0) {
                stringBuilder.append(System.getProperty("line.separator"));
            }
            for (int j = 0; j < h - i - 1; j++) {
                stringBuilder.append(" ");
            }
            for (int j = 0; j < (i + 1) * 2 - 1; j++) {
                stringBuilder.append("^");
            }
            for (int j = 0; j < h - i - 1; j++) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
