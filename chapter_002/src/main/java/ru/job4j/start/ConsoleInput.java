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
}
