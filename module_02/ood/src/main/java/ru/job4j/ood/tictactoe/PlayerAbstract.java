package ru.job4j.ood.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerAbstract implements Player {
    private final static Logger LOGGER = LogManager.getLogger(PlayerAbstract.class);
    private final String name;
    private int token;

    public PlayerAbstract(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public int getToken() {
        return this.token;
    }

}
