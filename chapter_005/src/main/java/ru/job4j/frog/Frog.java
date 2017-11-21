package ru.job4j.frog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Часть 005. Collections. Pro.[#146].
 * Тестовое задание №2.
 * Поиск кратчайшего пути по круговой сетке.
 */
class Frog {

    class Point {
        int stepsToPoint = 0;
        int ring;
        int sector;

        Point(int ring, int sector) {
            this.ring = ring;
            this.sector = sector;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Point point = (Point) o;

            return ring == point.ring && sector == point.sector;
        }

        @Override
        public int hashCode() {
            int result = ring;
            result = numberOfSectors * result + sector;
            return result;
        }
    }

    private int numberOfSectors = 16;
    private int numberOfRings = 10;
    private int numberOfCellsPerTurn = 3;

    public Frog() {
    }

    public Frog(int numberOfSectors, int numberOfRings, int numberOfCellsPerTurn) {
        this.numberOfSectors = numberOfSectors;
        this.numberOfRings = numberOfRings;
        this.numberOfCellsPerTurn = numberOfCellsPerTurn;
    }


    private Point frog;
    private Point star;
    private Set<Point> trees;
    private Map<Integer, Point> visited;

    /**
     *
     * @param args args[0], args[1] координаты лягушки (кольцо, сектор);
     *             args[2], args[3] координаты звезды (кольцо, сектор);
     *             args[4] количество деревьев;
     *             args[5]... координаты деревьев (кольцо, сектор).
     *             Значения кольцо и сектор должны быть больше нуля
     *             и в рамках заданных при создании класса через конструктор.
     * @return Список шагов или null если до звезды дойти невозможно.
     */
    public List<Point> findTheShortestPath(int...args) {
        frog = new Point(args[0], args[1]);
        star = new Point(args[2], args[3]);
        trees = new HashSet<>();
        if (args[4] > 0) {
            for (int i = 0; i < args[4]; i++) {
                trees.add(new Point(args[5 + i * 2], args[6 + i * 2]));
            }
        }

        this.visited = new HashMap<>();
        return findThePath(frog, 0);
    }

    private LinkedList<Point> findThePath(Point frog, int step) {

        LinkedList<Point> result = null;
        if (distanceBetweenPoint(frog, star) == numberOfCellsPerTurn) {

            if (star.stepsToPoint == 0 || star.stepsToPoint > step + 1) {
                frog.stepsToPoint = step;
                star.stepsToPoint = step + 1;
                result = new LinkedList<>();
                result.add(star);
            }

        } else if ((visited.putIfAbsent(frog.hashCode(), frog) == null || visited.get(frog.hashCode()).stepsToPoint > step)
                && (star.stepsToPoint == 0 || star.stepsToPoint > step + 1)) {

            visited.get(frog.hashCode()).stepsToPoint = step;

            for (int i = -numberOfCellsPerTurn + 1; i < numberOfCellsPerTurn; i++) {

                Point next = nextPoint(frog, i, numberOfCellsPerTurn - Math.abs(i));

                if (next != null && !trees.contains(next)) {

                    LinkedList<Point> path = findThePath(next, step + 1);

                    if (path != null) {
                        path.addFirst(next);
                        result = path;
                    }
                }
            }
        }

        return result;
    }


    private Point nextPoint(Point point, int ringDistance, int sectorDistance) {
        int ring = point.ring + ringDistance;
        if (ring > 0 && ring <= numberOfRings) {
            int sector = point.sector + sectorDistance;
            return new Point(ring, sector > numberOfSectors ? sector - numberOfSectors : sector);
        }
        return null;
    }

    private int distanceBetweenPoint(Point pointA, Point pointB) {
        return Math.abs(pointA.ring - pointB.ring)
                + pointB.sector - pointA.sector
                + (pointA.sector < pointB.sector ? 0 : numberOfSectors);
    }

    private void printResult(List<Point> steps) {
        if (steps != null) {
            System.out.println(String.format("Достал до звезды за %s шагов", steps.size()));
            for (Point point : steps) {
                System.out.println(String.format("Щаг %2s, кольцо-%2s, сектор-%2s", point.stepsToPoint, point.ring, point.sector));
            }
        } else {
            System.out.println("Звезда недостижима!");
        }
    }

    public static void main(String[] args) {
        Frog frog = new Frog();
//        Frog frog = new Frog(16,10,3);
        List<Point> steps;

        System.out.println("Вариант с проходом");
        steps = frog.findTheShortestPath(1, 1, 1, 1, 3, 1, 4, 2, 3, 3, 1);
        frog.printResult(steps);

        System.out.println();
        System.out.println("Вариант без прохода");
        steps = frog.findTheShortestPath(1, 1, 1, 1, 3, 1, 4, 2, 3, 3, 2);
        frog.printResult(steps);
    }
}

