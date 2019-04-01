package ru.job4j.ood.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIRandom implements AI {
    private final static Logger LOGGER = LogManager.getLogger(AIRandom.class);

    @Override
    public int move(int[][] field, int winSize, int token) {
        LOGGER.traceEntry();
        while (true) {
            int x = (int) (Math.random() * field.length);
            int y = (int) (Math.random() * field.length);
            if (field[y][x] == 0) {
                return y * field.length + x;
            }
        }
    }
}
