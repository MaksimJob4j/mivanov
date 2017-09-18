package ru.job4j.start;

/**
 * StubInput.
 */
public class StubInput implements Input {
    /**
     * Ответы.
     */
    private String[] answers;
    /**
     * Номер ответа.
     */
    private int position = 0;

    /**
     *
     * @param answers ответы.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }
    @Override
    public String ask(String question) {
        return answers[position++];
    }
}
