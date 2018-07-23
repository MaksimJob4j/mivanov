package ru.job4j.threads;

public class CalcWords implements Runnable {
    public enum StartType {
        WORD,
        WHITESPACES
    }
    private StartType type;
    private String string;

    private CalcWords(StartType type, String string) {
        this.type = type;
        this.string = string;
    }

    private void whiteSpaces() {
        int result = 0;
        for (int i = 0; i < string.length() && !Thread.currentThread().isInterrupted(); i++) {
            if (string.charAt(i) == ' ') {
                System.out.printf("Найдено %s пробелов \n", ++result);
            }
        }
    }

    private void words() {
        int result = 0;
        String s = string.trim();
        for (int i = 0; i < s.length() && !Thread.currentThread().isInterrupted(); i++) {
            if (s.charAt(i) == ' ' && string.charAt(i - 1) != ' ') {
                System.out.printf("Найдено %s слов \n", ++result);
            }
        }
        System.out.printf("Найдено %s слов \n", ++result);
    }


    @Override
    public void run() {
        if (type.equals(StartType.WHITESPACES)) {
            whiteSpaces();
        } else {
            words();
        }

    }

    static String generateString(int wordsAmount) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordsAmount; i++) {
            for (int j = 0; j < Math.random() * 10; j++) {
                sb.append(letters.charAt((int) (Math.random() * (letters.length() - 1))));
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    static void start(String string) {

        Thread threadWhiteSpaces = new Thread(new CalcWords(StartType.WHITESPACES, string));
        Thread threadWords = new Thread(new CalcWords(StartType.WORD, string));

        System.out.println("Программа запущена");

        threadWhiteSpaces.start();
        threadWords.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadWords.interrupt();
        threadWhiteSpaces.interrupt();

        try {
            threadWhiteSpaces.join();
            threadWords.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершена");
    }

    public static void main(String[] args) {
        CalcWords.start(generateString(200000));
    }
}
