package ru.job4j.start;

import java.util.ArrayList;

/**
 * StubInput.
 */
public class StubInput implements Input {
    /**
     * Ответы.
     */
    private ArrayList<String> answers;
    /**
     * Номер ответа.
     */
    private int position = 0;

    /**
     *
     * @param answers ответы.
     */
    public StubInput(ArrayList<String> answers) {
        this.answers = answers;
    }
    @Override
    public String ask(String question) {
        return answers.get(position++);
    }

    @Override
    public int ask(String question, ArrayList<Integer> range) {
        return Integer.parseInt(answers.get(position++));
    }
}
