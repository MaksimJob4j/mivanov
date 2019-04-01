package ru.job4j.ood.tictactoe;

public interface Player {

    int move(int[][] field, int winSize);

    String getName();

    void setToken(int i);

    int getToken();
}
