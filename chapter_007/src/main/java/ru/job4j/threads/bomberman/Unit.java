package ru.job4j.threads.bomberman;

public class Unit {

    UnitType type;
    String name;
    int positionX;
    int positionY;

    public Unit(UnitType type, String name) {
        this.type = type;
        this.name = name;
    }

}
