package ru.job4j.threads.bomberman;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BomberMan {
    private final int lengthY;
    private final int lengthX;
    private final ReentrantLock[][] board;

    public BomberMan(int lengthX, int lengthY) {
        this.lengthY = lengthY;
        this.lengthX = lengthX;
        board = new ReentrantLock[lengthY][lengthX];
    }

    public void makeBoard() {
        for (int i = 0; i < lengthY; i++) {
            for (int j = 0; j < lengthX; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }

    public void startMoving(Unit unit) {
        new Thread(() -> {
            try {
                if (board[unit.positionY][unit.positionX].tryLock(10, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " встал на клетку " + unit.positionX + " " + unit.positionY);
                    while (!Thread.currentThread().isInterrupted()) {
                        if (tryMove(unit, getMove(unit))) {
                            Thread.sleep(1000);
                        }
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " не зашел в игру. Клетка " + unit.positionX + " " + unit.positionY + " занята больше 10 секунд.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private int[] getMove(Unit unit) {
        int[] move = {0, 0};
        do {
            move[0] = (int) (Math.random() * 3) - 1;
            move[1] = (int) (Math.random() * 3) - 1;
        } while (move[0] == 0 && move[1] == 0
                || unit.positionX + move[0] == lengthX
                || unit.positionX + move[0] == -1
                || unit.positionY + move[1] == lengthY
                || unit.positionY + move[1] == -1);
        return move;
    }

    private boolean tryMove(Unit unit, int[] move) {
        boolean result = false;
        int newPositionX = unit.positionX + move[0];
        int newPositionY = unit.positionY + move[1];
        if (tryMove(newPositionX, newPositionY)) {
            unit.positionX = newPositionX;
            unit.positionY = newPositionY;
            board[newPositionY - move[1]][newPositionX - move[0]].unlock();
            System.out.println(Thread.currentThread().getName() + " переместился на клетку " + newPositionX + " " + newPositionY);
            result = true;
        }
        return result;
    }

    private boolean tryMove(int positionX, int positionY) {
        boolean result = false;
        try {
            result = board[positionY][positionX].tryLock(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        BomberMan bomberMan = new BomberMan(10, 10);
        bomberMan.makeBoard();

        for (int i = 0; i < 100; i++) {
            bomberMan.startMoving(new Unit(5, 5));
        }
    }


}
