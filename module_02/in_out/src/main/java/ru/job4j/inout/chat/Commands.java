package ru.job4j.inout.chat;

public enum Commands {
    STOP("стоп", "IE перестает выдавать сообщения"),
    CONTINUE("продолжить", "IE снова включается в разговор"),
    END("закончить", "Завершение работы чата");

    private String keyWord;
    private String description;

    private Commands(String keyWord, String description) {
        this.keyWord = keyWord;
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getDescription() {
        return description;
    }
    
    public static String getCommandsList() {
        StringBuilder result = new StringBuilder();
        for (Commands c: Commands.values()) {
            result.append(" '");
            result.append(c.keyWord);
            result.append("' - ");
            result.append(c.description);
            result.append(System.lineSeparator());
        }
        return result.toString();
    }
}
