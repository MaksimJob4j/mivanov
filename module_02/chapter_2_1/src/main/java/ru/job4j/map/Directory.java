package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 8. Реализовать собственную структуру данных - справочник. [#1008]
 * Справочник должен быть унифицирован через генерики.
 * Методы:
 * - boolean insert(T key, V value);
 * - V get(T key);
 * - boolean delete(T key);
 * Реализовывать итератор.
 * Внутренняя реализация должна использовать массив.
 * нужно обеспечить фиксированное время вставки и получение.
 * Коллизии реализовывать не надо. если ключ уже есть. то возвращать false
 */
public class Directory<T, V> implements Iterable<T> {

    private class Entry<T, V> {

        T key;
        V value;
        Entry<T, V> next;
        public Entry(T key, V value, Entry<T, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

    }
    /**
     * Длина массива по умолчанию равна 16.
     */
    private int capacity = 16;

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

    /**
     * Массив - хранилище пар ключ-значение.
     */
    private Entry<T, V>[] table = new Entry[capacity];

    public boolean insert(T key, V value) {
        boolean result = false;
        if (key != null && !contains(key)) {
            if (++size > threshold) {
                riseCapacity();
            }
            table[index(key)] = new Entry<T, V>(key, value, table[index(key)]);
            result = true;
        }
        return result;
    }

    private int index(T key) {
        int result = key.hashCode() % capacity;
        return result > 0 ? result : -result;
    }

    private void riseCapacity() {
        capacity *= 2;
        Entry<T, V>[] newTable = new Entry[capacity];
        for (Entry<T, V> entry: table) {
            while (entry != null) {
                newTable[index(entry.key)] = new Entry<>(entry.key, entry.value, newTable[index(entry.key)]);
                entry = entry.next;
            }
        }
        table = newTable;
        threshold = (int) (capacity * loadFactor);
    }

    private boolean contains(T key) {
        boolean result = false;
        if (key != null) {
            Entry<T, V> entry = table[index(key)];
            while (entry != null && !result) {
                if (entry.key.equals(key)) {
                    result = true;
                } else {
                    entry = entry.next;
                }
            }
        }
        return result;
    }

    public V get(T key) {
        V result = null;
        if (key != null) {
            Entry<T, V> entry = table[index(key)];
            while (entry != null && result == null) {
                if (entry.key.equals(key)) {
                    result = entry.value;
                } else {
                    entry = entry.next;
                }
            }
        }
        return result;
    }

    public boolean delete(T key) {
        boolean result = false;
        if (key != null) {
            Entry<T, V> entry = table[index(key)];
            if (entry != null) {
                if (entry.key.equals(key)) {
                    table[index(key)] = entry.next;
                    size--;
                    result = true;
                } else {
                    while (entry.next != null && !result) {
                        if (entry.next.key.equals(key)) {
                            entry.next = entry.next.next;
                            size--;
                            result = true;
                        } else {
                            entry = entry.next;
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * Итератор по ключам.
     * @return Iterator<T>.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Entry<T, V> entry;
            int itIndex = -1;
            boolean needEntry = true;

            @Override
            public boolean hasNext() {

                if (itIndex < capacity && needEntry) {
                    if (entry != null) {
                        entry = entry.next;
                    }
                    while (entry == null && ++itIndex < capacity) {
                        entry = table[itIndex];
                    }
                    needEntry = false;
                }
                return itIndex != capacity && entry != null;
            }

            @Override
            public T next() {

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                needEntry = true;
                return entry.key;
            }
        };
    }

}
