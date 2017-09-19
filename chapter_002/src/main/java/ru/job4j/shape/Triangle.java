package ru.job4j.shape;

/**
 * Triangle.
 */
public class Triangle implements Shape {
    /**
     *
     * @return Triangle.
     */
    @Override
    public String pic() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = 4;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i + 1; j++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

}
