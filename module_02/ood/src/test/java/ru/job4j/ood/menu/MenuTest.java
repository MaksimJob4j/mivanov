package ru.job4j.ood.menu;

import org.junit.Test;

import static org.junit.Assert.*;

public class MenuTest {

    @Test
    public void menuTest() {
        Menu menu = new Menu(".");
        assertEquals(menu.printMenu(), "");

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

        String ls = System.lineSeparator();
        StringBuilder builder = new StringBuilder();
        builder.append("- 1 first").append(ls)
                .append("-- 1.a first A").append(ls)
                .append("-- 1.b first B").append(ls)
                .append("--- 1.b.AA first B AA").append(ls)
                .append("-- 1.c first C").append(ls)
                .append("- 2 second").append(ls);

        assertEquals(menu.printMenu(), builder.toString());
        assertEquals(menu.perform("1.b.AA"), "Action not defined. Action \"first B AA\" not defined.");
        firstBAA.setAction(new MenuAction() {
            @Override
            public void perform() {
                System.out.println("Some action");
            }
        });
        assertEquals(menu.perform("1.b.AA"), "ok");
        assertEquals(menu.perform("3"), "Wrong key. Key \"3\" not supported.");
    }
}