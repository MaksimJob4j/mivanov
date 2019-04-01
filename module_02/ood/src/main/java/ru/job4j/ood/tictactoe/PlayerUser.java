package ru.job4j.ood.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class PlayerUser extends PlayerAbstract {
    private final static Logger LOGGER = LogManager.getLogger(PlayerUser.class);

    public PlayerUser(String name) {
        super(name);
    }

    @Override
    public int move(int[][] field, int winSize) {
        LOGGER.traceEntry();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter coordinates (whole numbers only X Y)");
                int x = Integer.parseInt(scanner.next());
                int y = Integer.parseInt(scanner.next());
                if (x >= 0 && x < field.length && y >= 0 && y < field.length) {
                    if (field[y][x] == 0) {
                        return y * field.length + x;
                    } else {
                        System.out.printf("The cell %d %d is already taken.%n", x, y);
                    }
                } else {
                    System.out.printf("Greater than or equal to 0 and less than %d%n", field.length);
                }
            } catch (NumberFormatException e) {
                LOGGER.error("Wrong number format", e);
            }
            System.out.println("Let's try again.");
        }
    }

}
