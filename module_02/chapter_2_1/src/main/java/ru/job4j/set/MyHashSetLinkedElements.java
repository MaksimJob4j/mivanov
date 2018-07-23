package ru.job4j.set;

import java.util.List;

public class MyHashSetLinkedElements<E> {
    private class Element<E> {
        E value;
        Element<E> next;
//        int position;

        Element(E value, Element<E> next) {
            this.value = value;
            this.next = next;
        }
    }
    /**
     * Длина массива по умолчанию равна 16.
     */
    private int capacity = 16;
    /**
     * Массив - хранилище ссылок на списки (цепочки) значений.
     */
    private Element<E>[] table = new Element[capacity];
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

    public MyHashSetLinkedElements() { }

    public MyHashSetLinkedElements(List<E> list) {
        for (E e: list) {
            this.add(e);
        }
    }

    boolean add(E value) {
        boolean result = false;
        if (value != null && !contains(value)) {
            if (++size > threshold) {
                riseCapacity();
            }
//            if (table[index(element)] == null) {
//                table[index(element)] = new Element<E>(element, null);
//            } else {
//                Element<E> tempElement = table[index(element)];
//                while (tempElement.next != null) {
//                    tempElement = tempElement.next;
//                }
//                tempElement.next = new Element<E>(element, null);

            table[index(value)] = new Element<E>(value, table[index(value)]);
            result = true;
        }
        return result;
    }

    boolean remove(E value) {
        boolean result = false;
        if (value != null && table[index(value)] != null) {
            Element<E> tempElement = table[index(value)];
            if (tempElement.value.equals(value)) {
                table[index(value)] = tempElement.next;
            } else {
                while (tempElement.next != null && !result) {
                    if (tempElement.next.value.equals(value)) {
                        tempElement.next = tempElement.next.next;
                        result = true;
                        size--;
                    } else {
                        tempElement = tempElement.next;
                    }
                }
            }
        }
        return result;
    }

    public boolean contains(E value) {
        boolean result = false;
        if (value != null && table[index(value)] != null) {
            Element<E> tempElement = table[index(value)];
            while (tempElement != null && !result) {
                if (tempElement.value.equals(value)) {
                    result = true;
                } else {
                    tempElement = tempElement.next;
                }
            }
        }
        return result;
    }

    private void riseCapacity() {
        capacity = capacity * 2;
        Element<E>[] newTable = new Element[capacity];
        for (Element<E> element: table) {
            while (element != null) {
                newTable[index(element.value)] = new Element<E>(element.value, newTable[index(element.value)]);
                element = element.next;
            }
        }
        threshold = (int) (capacity * loadFactor);
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
            if (table[i] != null) {
                Element<E> element = table[i];
                while (element != null) {
                    sb.append(element.value);
                    sb.append(", ");
                    element = element.next;
                }
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
