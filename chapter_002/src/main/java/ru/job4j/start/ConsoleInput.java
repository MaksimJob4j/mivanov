package ru.job4j.start;

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
    public int ask(String question, int[] range) {
        int key = Integer.parseInt(this.ask(question));
        boolean exist = false;
        for (int value: range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Out of menu.");
        }
    }
}
