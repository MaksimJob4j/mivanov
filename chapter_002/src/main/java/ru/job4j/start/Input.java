package ru.job4j.start;

/**
 * Input.
 */
public interface Input {
    /**
     *
     * @param question question.
     * @return console input.
     */
    String ask(String question);

    /**
     *
     * @param question question.
     * @param range range.
     * @return int.
     */
    int ask(String question, int[] range);

}
