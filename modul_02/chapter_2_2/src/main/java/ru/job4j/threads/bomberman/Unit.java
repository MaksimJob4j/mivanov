package ru.job4j.threads.bomberman;

class Unit {

    UnitType type;
    String name;
    int positionX;
    int positionY;

    Unit(UnitType type, String name) {
        this.type = type;
        this.name = name;
    }

}
