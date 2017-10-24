package ru.job4j.set;

//import ru.job4j.list.DynamicalList;
//import ru.job4j.list.MyLinkedList;

import java.util.List;
import java.util.LinkedList;

/**
 * @param <E>
 */
public class MyHashSet<E> {
    /**
     * Длина массива по умолчанию равна 16.
     */
    private int capacity = 16;
    /**
     * Массив - хранилище ссылок на списки (цепочки) значений.
     */
    private List<E>[] table = new LinkedList[capacity];
    /**
     * Коэффициент загрузки.
     */
    private float loadFactor = 0.75f;
    /**
     * Предельное количество элементов, при достижении которого, размер хэш-таблицы увеличивается вдвое.
     */
    private int threshold = (int) (capacity * loadFactor);
    /**
     * Количество элементов.
     */
    private int size = 0;

    public MyHashSet() { }

    public MyHashSet(List<E> list) {
        for (E e: list) {
            this.add(e);
        }
    }

    boolean add(E element) {
        boolean result = false;
        if (element != null && !contains(element)) {
            if (++size * loadFactor > threshold) {
                riseCapacity();
            }
            if (table[index(element)] == null) {
                table[index(element)] = new LinkedList<>();
            }
            table[index(element)].add(element);

            result = true;
        }

        return result;
    }

    boolean remove(E element) {
        boolean result = false;
        if (table[index(element)] != null && table[index(element)].remove(element)) {
            result = true;
            size--;
        }
        return result;
    }

    private boolean contains(E element) {
        return (table[index(element)] != null && table[index(element)].contains(element));
    }

    private void riseCapacity() {
        capacity = capacity * 2;
        // Этот код выдает ошибку при первом же удвоении массива списков
        // java.lang.OutOfMemoryError: Java heap space
        List<E>[] newTable = new LinkedList[capacity];
        for (List<E> list: table) {
            if (list != null) {
                for (E e : list) {
                    if (e != null) {
                        if (newTable[index(e)] == null) {
                            newTable[index(e)] = new LinkedList<>();
                        }
                        newTable[index(e)].add(e);
                    }
                }
            }
        }
        table = newTable;
    }

    private int index(E e) {
        int result = e.hashCode() % capacity;
        return result > 0 ? result :  -result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i].size() > 0) {
                sb.append(table[i]);
                sb.append(", ");
            }
        }
        try {
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.deleteCharAt(sb.lastIndexOf(" "));
        } catch (Exception e) { }
        sb.append("]");
        return sb.toString();
    }
}
