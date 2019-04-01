package ru.job4j.ood.menu;

import org.junit.Test;

import static org.junit.Assert.*;

public class MenuTest {

    @Test
    public void whenNewMenuThenItEmpty() {
        Menu menu = new Menu();
        assertEquals(menu.printMenu(), "");
    }

    @Test
    public void whenAddItemThenMenuHasIt() {
        Menu menu = createMenu();
        String ls = System.lineSeparator();
        String result = "- 1 first" + ls
                + "-- 1.a first A" + ls
                + "-- 1.b first B" + ls
                + "--- 1.b.AA first B AA" + ls
                + "-- 1.c first C" + ls
                + "- 2 second" + ls;
        assertEquals(menu.printMenu(), result);
    }

    @Test
    public void whenActionNotDefinedThenGetItsMessage() {
        Menu menu = createMenu();
        assertEquals(menu.perform("1.b"), "Action not defined. Action \"first B\" not defined.");
    }

    @Test
    public void whenWrongKeyThenGetItsMessage() {
        Menu menu = createMenu();
        assertEquals(menu.perform("3"), "Wrong key. Key \"3\" not supported.");
    }

    @Test
    public void whenActionExistThenPerformItAmdGetOkMessage() {
        Menu menu = createMenu();
        assertEquals(menu.perform("1.b.AA"), "ok");
    }

    private Menu createMenu() {
        Menu menu = new Menu();
        MenuItem first = new MenuItem("1", "first");
        MenuItem firstA = new MenuItem("a", "first A");
        MenuItem firstB = new MenuItem("b", "first B");
        MenuItem firstBAA = new MenuItem("AA", "first B AA");
        firstB.addSubItem(firstBAA);
        MenuItem firstC = new MenuItem("c", "first C");
        first.addSubItem(firstA);
        first.addSubItem(firstB);
        first.addSubItem(firstC);
        menu.addItem(first);
        MenuItem second = new MenuItem("2", "second");
        menu.addItem(second);
        firstBAA.setAction(new MenuAction() {
            @Override
            public void perform() {
                System.out.println("Some action");
            }
        });
        return menu;
    }
}