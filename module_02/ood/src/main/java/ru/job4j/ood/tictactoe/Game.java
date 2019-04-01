package ru.job4j.ood.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Game {
    private final static Logger LOGGER = LogManager.getLogger(Game.class);
    private final int fieldSize;
    private final int winSize;
    private final Player first;
    private final Player second;
    private final Map<Integer, Character> tokenSymbols = Map.of(-1, 'X', 1, '0');

    public Game(int fieldSize, int winSize, Player first, Player second) {
        this.fieldSize = fieldSize;
        this.winSize = winSize;
        this.first = first;
        this.second = second;
    }

    public void start(boolean firstMove) {
        LOGGER.traceEntry();
        int[][] field = new int[this.fieldSize][this.fieldSize];
        first.setToken(1);
        second.setToken(-1);
        int moveCount = 0;
        boolean noWinner = true;
        Player player = firstMove ? this.first : this.second;
        while (noWinner && moveCount < this.fieldSize * this.fieldSize) {
            drawField(field);
            int move = player.move(field, this.winSize);
            field[move / this.fieldSize][move % this.fieldSize] = player.getToken();
            System.out.format(
                    "Player %s(%s) move is [%s,%s]%n",
                    player.getName(),
                    this.tokenSymbols.get(player.getToken()),
                    move % this.fieldSize,
                    move / this.fieldSize);
            moveCount++;
            if (isWin(field, move)) {
                noWinner = false;
                System.out.format(
                        "Player %s(%s) win!%n",
                        player.getName(),
                        this.tokenSymbols.get(player.getToken()));
            } else {
                player = player == first ? second : first;
            }
        }
        if (noWinner) {
            System.out.println("No one wins!");
        }
    }

    private void drawField(int[][] field) {
        LOGGER.traceEntry();
        System.out.print("    ");
        for (int i = 0; i < this.fieldSize; i++) {
            System.out.printf("%-3s ", i);
        }
        System.out.println();
        for (int i = 0; i < this.fieldSize; i++) {
            System.out.printf("%-4s", i);
            for (int j = 0; j < this.fieldSize; j++) {
                int token = field[i][j];
                System.out.print(token == 0
                        ? "___ "
                        : String.format("_%s_ ", this.tokenSymbols.get(token)));
            }
            System.out.println();
        }
    }

    public boolean isWin(int[][] field, int move) {
        LOGGER.traceEntry();
        int x = move % this.fieldSize;
        int y = move / this.fieldSize;
        int token = field[y][x];
        int xCount = 0;
        int yCount = 0;
        int xyCount = 0;
        int yxCount = 0;
        for (int i = -(this.winSize - 1); i < this.winSize; i++) {
            xCount = calculateCounter(field, x + i, y, token, xCount);
            yCount = calculateCounter(field, x, y + i, token, yCount);
            xyCount = calculateCounter(field, x + i, y + i, token, xyCount);
            yxCount = calculateCounter(field, x + i, y - i, token, yxCount);
            if (xCount == this.winSize || yCount == this.winSize
                    || xyCount == this.winSize || yxCount == this.winSize) {
                return true;
            }
        }
        return false;
    }

    private int calculateCounter(int[][] field, int x, int y, int token, int counter) {
        if (
                x >= 0
                && x < this.fieldSize
                && y >= 0
                && y < this.fieldSize
                && field[y][x] == token) {
            return counter + 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Player first = new PlayerUser("Max");
//        AI ai = new AIRandom();
        AI ai = new AISmart();
        Player second = new PlayerComputer("AI", ai);
        Game game = new Game(10, 5, first, second);
        game.start(true);
    }
}
