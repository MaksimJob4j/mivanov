package ru.job4j.start;

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
    public int ask(String question, int[] range) {

        int key = -1;
        boolean isCorrect = false;

        while (!isCorrect) {
            try {
                key = super.ask(question, range);

                isCorrect = true;
            } catch (MenuOutException moe) {
//                moe.printStackTrace();
                System.out.println("Введите значение из диапазона меню.");
            } catch (NumberFormatException nfe) {
//                nfe.printStackTrace();
                System.out.println("Введите корректное значение.");
            }
        }
        return key;
    }
}
