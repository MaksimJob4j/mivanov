package ru.job4j.start;

import java.util.ArrayList;

/**
 * ValidateInput.
 */
public class ValidateInput extends ConsoleInput {

    /**
     *
     * @param question question.
     * @param range range.
     * @return int
     */
    @Override
    public int ask(String question, ArrayList<Integer> range) {

        int key = -1;
        boolean isCorrect = false;

        while (!isCorrect) {
            try {
                key = super.ask(question, range);
                isCorrect = true;
            } catch (MenuOutException moe) {
                System.out.println("Введите значение из диапазона меню.");
            } catch (NumberFormatException nfe) {
                System.out.println("Введите корректное значение.");
            }
        }
        return key;
    }
}
