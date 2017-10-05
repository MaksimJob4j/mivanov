package ru.job4j.start;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * ConsoleInput.
 */
public class ConsoleInput implements Input {

    /**
     * Scanner.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     *
     * @param question question.
     * @return answer.
     */
    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    /**
     *
     * @param question question.
     * @param range range.
     * @return int.
     */
    @Override
    public int ask(String question, ArrayList<Integer> range) {
        int key = Integer.parseInt(this.ask(question));
        if (range.contains(key)) {
            return key;
        } else {
            throw new MenuOutException("Out of menu.");
        }
    }
}
