package ru.job4j.collectionstask;

//import java.util.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * .
 */
public class CollectionTimeTest {


    /**
     * .
     * @param collection collection
     * @param amount amount
     * @return time
     */
    public static long add(Collection<String> collection, int amount) {
        Long time = 0L;
        String string;
        for (int i = 0; i < amount; i++) {
            string = String.valueOf(Math.random());
            time -= System.currentTimeMillis();
            collection.add(string);
            time += System.currentTimeMillis();
        }
        return time;
    }

    /**
     * .
     * @param collection collection
     * @param amount amount
     * @return time
     */
    public static long delete(Collection<String> collection, int amount) {
        Long time = 0L;
        Iterator iterator = collection.iterator();
        for (int i = 0; i < amount; i++) {
            iterator.next();
            time -= System.currentTimeMillis();
            iterator.remove();
            time += System.currentTimeMillis();
        }
        return time;
    }

    /**
     *
     * @param args .
     */
    public static void main(String[] args) {
        int amountAdd = 1000000;
        int amountDel = 400000;
        List<CollectionItem> collectionItems = new ArrayList<>();
        collectionItems.add(new CollectionItem(new LinkedList<String>()));
        collectionItems.add(new CollectionItem(new ArrayList<String>()));
        collectionItems.add(new CollectionItem(new TreeSet<String>()));

        for (CollectionItem collectionItem: collectionItems) {
            collectionItem.setTimeAdd(CollectionTimeTest.add(collectionItem.getCollection(), amountAdd));
            collectionItem.setTimeDel(CollectionTimeTest.delete(collectionItem.getCollection(), amountDel));
        }

        Collections.sort(collectionItems, CollectionItem.getAddComparator());

        for (CollectionItem collectionItem: collectionItems) {
            System.out.printf("Класс %S, время на добавление %S элементов %Sms.",
                    collectionItem.getCollection().getClass(), amountAdd,
                    collectionItem.getTimeAdd());
            System.out.println();

        }
        System.out.println();

        Collections.sort(collectionItems, CollectionItem.getDelComparator());

        for (CollectionItem collectionItem: collectionItems) {
            System.out.printf("Класс %S, время на удаление %S элементов %Sms.",
                    collectionItem.getCollection().getClass(), amountDel,
                    collectionItem.getTimeDel());
            System.out.println();

        }

    }
}
