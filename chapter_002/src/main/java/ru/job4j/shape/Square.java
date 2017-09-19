package ru.job4j.shape;

/**
 * Square.
 */
public class Square implements Shape {

    /**
     *
     * @return Square.
     */
    @Override
    public String pic() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = 4;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }
}
