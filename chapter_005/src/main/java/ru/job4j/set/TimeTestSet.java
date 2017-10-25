package ru.job4j.set;

import java.util.*;

/**
 * TimeTestSet.
 */
public class TimeTestSet<E> {

    private String makeString(String name, Long t1, Long t2, int f, int nf) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----");
        sb.append(name);
        sb.append(":\n");
        sb.append("Запись - ");
        sb.append(t1);
        sb.append("мс. ");
        sb.append("Чтение - ");
        sb.append(t2);
        sb.append("мс (");
        sb.append(f);
        sb.append("шт найдено / ");
        sb.append(nf);
        sb.append("шт не найдено)\n");
        return sb.toString();
    }


    private String speedTest(List<E> list, List<E> listRead, int amountAdd, int amountRead) {

        Long timeAdd;
        Long timeRead;
        int find, notFind;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Тест скорости записи ");
        stringBuilder.append(amountAdd / 1000);
        stringBuilder.append("тыс. и поиска ");
        stringBuilder.append(amountRead / 1000);
        stringBuilder.append("тыс. элементов.\n");


        timeAdd = -System.currentTimeMillis();
        Set<E> set1 = new HashSet<>(list);
        timeAdd += System.currentTimeMillis();

        find = 0;
        notFind = 0;
        timeRead = -System.currentTimeMillis();
        for (E e : listRead) {
            if (set1.contains(e)) {
                find++;
            } else {
                notFind++;
            }
        }
        timeRead += System.currentTimeMillis();

        stringBuilder.append(makeString("HashSet", timeAdd, timeRead, find, notFind));

        timeAdd = -System.currentTimeMillis();
        SimpleLinkedSet<E> set2 = new SimpleLinkedSet<>(list);
        timeAdd += System.currentTimeMillis();

        find = 0;
        notFind = 0;
        timeRead = -System.currentTimeMillis();
        for (E e : listRead) {
            if (set2.contains(e)) {
                find++;
            } else {
                notFind++;
            }
        }
        timeRead += System.currentTimeMillis();

        stringBuilder.append(makeString("Мой Set на моем LinkedList", timeAdd, timeRead, find, notFind));

        timeAdd = -System.currentTimeMillis();
        SimpleSet<E> set3 = new SimpleSet<>(list);
        timeAdd += System.currentTimeMillis();

        find = 0;
        notFind = 0;
        timeRead = -System.currentTimeMillis();
        for (E e: listRead) {
            if (set3.contains(e)) {
                find++;
            } else {
                notFind++;
            }
        }
        timeRead += System.currentTimeMillis();

        stringBuilder.append(makeString("Мой Set на моем ArrayList", timeAdd, timeRead, find, notFind));

        timeAdd = -System.currentTimeMillis();
        MyHashSet<E> set4 = new MyHashSet<>(list);
        timeAdd += System.currentTimeMillis();

        find = 0;
        notFind = 0;
        timeRead = -System.currentTimeMillis();
        for (E e : listRead) {
            if (set4.contains(e)) {
                find++;
            } else {
                notFind++;
            }
        }
        timeRead += System.currentTimeMillis();

        stringBuilder.append(makeString("Мой HashSet на Collections LinkedList", timeAdd, timeRead, find, notFind));

        timeAdd = -System.currentTimeMillis();
        MyHashSetLinkedElements<E> set5 = new MyHashSetLinkedElements<>(list);
        timeAdd += System.currentTimeMillis();

        find = 0;
        notFind = 0;
        timeRead = -System.currentTimeMillis();
        for (E e : listRead) {
            if (set5.contains(e)) {
                find++;
            } else {
                notFind++;
            }
        }
        timeRead += System.currentTimeMillis();

        stringBuilder.append(makeString("Мой HashSet без коллекций", timeAdd, timeRead, find, notFind));

        return stringBuilder.toString();

    }

    public static void main(String[] args) {

        TimeTestSet<Integer> integerTimeTestSet = new TimeTestSet<>();
        TimeTestSet<String> stringTimeTestSet = new TimeTestSet<>();

        int max = 1000000;
        int amountAdd = 1000000;
        int amountRead = 10000;

        // список для вставки
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < amountAdd; i++) {
            list.add((int) (Math.random() * max));
        }

        // список для чтения (проверки на вхождение)
        List<Integer> listRead = new ArrayList<>();
        for (int i = 0; i < amountRead; i++) {
            listRead.add((int) (Math.random() * max));
        }
        System.out.println("Integer");
        System.out.println(integerTimeTestSet.speedTest(list, listRead, amountAdd, amountRead));


        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // список для вставки
        List<String> listSt = new ArrayList<>();
        for (int i = 0; i < amountAdd; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < Math.random() * 20; j++) {
                stringBuilder.append(letters.charAt((int) (Math.random() * (letters.length() - 1))));
            }
            listSt.add(stringBuilder.toString());
        }

        // список для чтения (проверки на вхождение)
        List<String> listReadSt = new ArrayList<>();
        for (int i = 0; i < amountRead / 2; i++) {
            listReadSt.add(listSt.get((int) (Math.random() * (listSt.size() - 1))));
            listReadSt.add(listSt.get((int) (Math.random() * (listSt.size() - 1))) + "a");

        }
        System.out.println("String");
        System.out.println(stringTimeTestSet.speedTest(listSt, listReadSt, amountAdd, amountRead));

    }

}

