package ru.job4j.ood.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AISmart implements AI {
    private final static Logger LOGGER = LogManager.getLogger(AISmart.class);
    private Set<Pattern> patterns;
    private int winSize;
    private int token;

    @Override
    public int move(int[][] field, int winSize, int token) {
        LOGGER.traceEntry();
        if (winSize != this.winSize && token != this.token) {
            this.winSize = winSize;
            this.token = token;
            trainAI();
        }
        TreeMap<Integer, List<Integer>> cells = new TreeMap<>((i1, i2) -> i2 - i1);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == 0) {
                    int cell = i * field.length + j;
                    int rate = checkCell(field, j, i);
                    if (cells.get(rate) == null) {
                        cells.put(rate, new LinkedList<>(Collections.singletonList(cell)));
                    } else {
                        cells.get(rate).add(cell);
                    }
                }
            }
        }
        return cells.firstEntry().getValue().get(0);
    }

    private int checkCell(int[][] field, int x, int y) {
        LOGGER.traceEntry();
        if (field[y][x] != 0) {
            return -1;
        }
        for (Pattern pattern: this.patterns) {
            field[y][x] = pattern.isOwn() ? this.token : -this.token;
            if (checkPattern(field, x, y, pattern.getSequence())) {
                field[y][x] = 0;
                return field.length * field.length - pattern.getPriority();
            }
        }
        field[y][x] = 0;
        return 0;
    }

    private boolean checkPattern(int[][] field, int x, int y, int[] pattern) {
        LOGGER.traceEntry();
        int xCount = 0;
        int yCount = 0;
        int xyCount = 0;
        int yxCount = 0;
        for (int i = -(pattern.length - 1); i < pattern.length; i++) {
            xCount = calculateCounter(field, x + i, y, pattern, xCount);
            yCount = calculateCounter(field, x, y + i, pattern, yCount);
            xyCount = calculateCounter(field, x + i, y + i, pattern, xyCount);
            yxCount = calculateCounter(field, x + i, y - i, pattern, yxCount);
            if (xCount == pattern.length
             || yCount == pattern.length
             || xyCount == pattern.length
             || yxCount == pattern.length) {
                return true;
            }
        }
        return false;
    }

    private int calculateCounter(int[][] field, int x, int y, int[] pattern, int counter) {
        LOGGER.traceEntry();
        if (x >= 0 && x < field.length && y >= 0 && y < field.length) {
            if (field[y][x] == pattern[counter]) {
                return counter + 1;
            } else if (field[y][x] == pattern[0]) {
                return 1;
            }
        }
        return 0;
    }

    private void trainAI() {
        LOGGER.traceEntry();
        this.patterns = new TreeSet<>();
        int priority = 0;
        // - winSize у игрока
        patterns.add(new Pattern(
                Arrays.stream(new int[this.winSize]).map(s -> s = this.token).toArray(),
                priority,
                true
        ));
        // - У СOПЕРНИКА winSize
        priority++;
        patterns.add(new Pattern(
                Arrays.stream(new int[this.winSize]).map(s -> s = -this.token).toArray(),
                priority,
                false
        ));
        // - winSize-1 + свободные поля с двух сторон
        priority++;
        int[] own2 = Arrays.stream(new int[this.winSize + 1]).sequential().map(s -> s = this.token).toArray();
        own2[0] = 0;
        own2[this.winSize] = 0;
        patterns.add(new Pattern(own2, priority, true));
        // - winSize-1 + свободныое поле с одной стороны
        priority++;
        int[] own3 = Arrays.stream(new int[this.winSize]).sequential().map(s -> s = this.token).toArray();
        own3[0] = 0;
        patterns.add(new Pattern(own3, priority, true));
        int[] own4 = Arrays.stream(new int[this.winSize]).sequential().map(s -> s = this.token).toArray();
        own4[this.winSize - 1] = 0;
        patterns.add(new Pattern(own4, priority, true));
        // - У СOПЕРНИКА winSize-1 + свободные поля с двух сторон
        priority++;
        int[] alien2 = Arrays.stream(new int[this.winSize + 1]).sequential().map(s -> s = -this.token).toArray();
        alien2[0] = 0;
        alien2[this.winSize] = 0;
        patterns.add(new Pattern(alien2, priority, false));
        for (int i = 0; i < this.winSize - 3; i++) {
            // - winSize-2 + свободные поля с двух сторон
            priority++;
            int[] own5 = Arrays.stream(new int[this.winSize + 1]).sequential().map(s -> s = this.token).toArray();
            for (int j = 0; j < i + 2; j++) {
                own5[j] = 0;
            }
            own5[winSize] = 0;
            patterns.add(new Pattern(own5, priority, true));
            int[] own6 = Arrays.stream(new int[this.winSize + 1]).sequential().map(s -> s = this.token).toArray();
            own6[0] = 0;
            for (int j = 0; j < i + 2; j++) {
                own6[this.winSize - j] = 0;
            }
            patterns.add(new Pattern(own6, priority, true));
            // - У СOПЕРНИКА winSize -2 + свободные поля с двух сторон
            priority++;
            int[] alien3 = Arrays.stream(new int[this.winSize + 1]).sequential().map(s -> s = -this.token).toArray();
            for (int j = 0; j < i + 2; j++) {
                alien3[j] = 0;
            }
            alien3[this.winSize] = 0;
            patterns.add(new Pattern(alien3, priority, false));
            int[] alien4 = Arrays.stream(new int[this.winSize + 1]).sequential().map(s -> s = -this.token).toArray();
            alien4[0] = 0;
            for (int j = 0; j < i + 2; j++) {
                alien4[this.winSize - j] = 0;
            }
            patterns.add(new Pattern(alien4, priority, false));
        }
    }
}