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
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                System.out.printf("Найдено %s пробелов \n", ++result);
            }
        }
    }

    private void words() {
        int result = 0;
        String s = string.trim();
        for (int i = 0; i < s.length(); i++) {
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

    public static void main(String[] args) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < Math.random() * 10; j++) {
                sb.append(letters.charAt((int) (Math.random() * (letters.length() - 1))));
            }
            sb.append(" ");
        }
        String string = sb.toString();
        new Thread(new CalcWords(StartType.WHITESPACES, string)).start();
        new Thread(new CalcWords(StartType.WORD, string)).start();
    }
}
