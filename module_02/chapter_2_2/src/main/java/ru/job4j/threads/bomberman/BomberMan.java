package ru.job4j.threads.bomberman;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class BomberMan {
    private final int lengthY;
    private final int lengthX;
    private final ReentrantLock[][] board;
    private AtomicBoolean gameOver = new AtomicBoolean(false);
    private final Unit man = new Unit(UnitType.MAN, "____BOMBERMAN___");
    private ConcurrentHashMap<Integer, Unit> units = new ConcurrentHashMap<>();

    private BomberMan(int lengthX, int lengthY) {
        this.lengthY = lengthY;
        this.lengthX = lengthX;
        board = new ReentrantLock[lengthY][lengthX];
    }

    private void makeBoard() {
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

    private void placeObstacles(int numberOfObstacles) {
        for (int i = 0; i < numberOfObstacles; i++) {
            placeUnit(new Unit(UnitType.OBSTACLE, "Препятствие-" + i));
        }
    }

    private void releaseMonsters(int numberOfMonsters) {
        for (int i = 0; i < numberOfMonsters; i++) {
            Unit monster = new Unit(UnitType.MONSTER, "Monster-" + i);
            new Thread(() -> {
                placeUnit(monster);
                while (!gameOver.get()) {
                    if (tryMove(monster, getMove(monster))) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    private void startMan(Unit man) {
        new Thread(() -> {
            placeUnit(man);
            int[] move;
            while (!gameOver.get()) {
                move = getMansMove(man);
                if (move != null) {
                    tryMove(man, move);
                }
            }
        }).start();
    }

    private void placeUnit(Unit unit) {
        int x, y;
        do {
            x = (int) (Math.random() * lengthX);
            y = (int) (Math.random() * lengthY);
        } while (board[y][x].isLocked() || !board[y][x].tryLock());
        System.out.println(unit.name + " встал на клетку " + x + " " + y);
        unit.positionX = x;
        unit.positionY = y;
        units.put(x + y * lengthX, unit);
    }

    private int[] getMansMove(Unit man) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getMove(man);
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
        if (units.get(newPositionX + newPositionY * lengthX) == man) {
            gameOver.set(true);
            System.out.println(unit.name + " поймал " + man.name + " на клетке " + newPositionX + " " + newPositionY);
        } else if (unit == man
                && units.get(newPositionX + newPositionY * lengthX) != null
                && units.get(newPositionX + newPositionY * lengthX).type.equals(UnitType.MONSTER)) {
            gameOver.set(true);
            System.out.println(man.name + " налетел на " + units.get(newPositionX + newPositionY * lengthX).name + " на клетке " + newPositionX + " " + newPositionY);
        }

        if (tryCage(newPositionX, newPositionY)) {
            units.remove(unit.positionX + unit.positionY * lengthX);
            unit.positionX = newPositionX;
            unit.positionY = newPositionY;
            units.put(newPositionX + newPositionY * lengthX, unit);
            board[newPositionY - move[1]][newPositionX - move[0]].unlock();
            System.out.println(unit.name + " переместился на клетку " + newPositionX + " " + newPositionY);
            result = true;
        }
        return result;
    }

    private boolean tryCage(int positionX, int positionY) {
        boolean result = false;
        try {
            result = board[positionY][positionX].tryLock(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    void startGame(int numberOfObstacles, int numberOfMonsters) {
        makeBoard();
        placeObstacles(numberOfObstacles);
        releaseMonsters(numberOfMonsters);
        startMan(man);
        while (!gameOver.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BomberMan bomberMan = new BomberMan(10, 10);
        bomberMan.startGame(7, 10);
    }

}
