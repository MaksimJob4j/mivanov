package ru.job4j.ood.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerComputer extends PlayerAbstract {
    private final static Logger LOGGER = LogManager.getLogger(PlayerComputer.class);
    private final AI ai;

    public PlayerComputer(String name, AI ai) {
        super(name);
        this.ai = ai;
    }

    @Override
    public int move(int[][] field, int winSize) {
        LOGGER.traceEntry();
        return ai.move(field, winSize, this.getToken());
    }
}
