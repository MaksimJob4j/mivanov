package ru.job4j.tree;


/**
 * Создайте BST - binary search tree (двоичное дерево поиска).
 * 1) добавьте метод add(E e);
 *
 * Помните
 * 1. Корень имеет только два дочерних элемента: left и right.
 *    Каждый из этих узлов в свою очередь, может быть корнем для своих левых и правых поддеревьев.
 * 2. Элемент слева от корня, меньше либо равен корню, а правый больше корня.
 * 3. Добавление элемента постарайтесь реализовать рекурсивно.
 *
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> extends Tree<E> {

    /**
     * Создайте BST - binary search tree (двоичное дерево поиска).
     1) добавьте метод add(E e);

     Помните
     1. Корень имеет только два дочерних элемента: left и right. Каждый из этих узлов в свою очередь, может быть корнем для своих левых и правых поддеревьев.
     2. Элемент слева от корня, меньше либо равен корню, а правый больше корня.
     3. Добавление элемента постарайтесь реализовать рекурсивно.
     * @param value
     * @return
     */


    /**
     * Добавление элементов в бинарное дерево.
     *
     * @param value элемент.
     * @return true усли новый элемент добавлен.
     */
    boolean add(E value) {
        boolean result = false;
        if (value != null) {
            if (getRoot() == null) {
                result = super.add(value, null);
            } else {
                result = super.add(findRoot(value, getRoot()), value);
            }
        }
        return result;
    }

    private E findRoot(E value, Node<E> node) {
        E result;
        if (node.children == null || node.children.size() == 0) {
            result = node.value;
        } else if (value.compareTo(node.value) > 0
                    && node.value.compareTo(node.children.get(0).value) > 0
                    || value.compareTo(node.value) < 0
                    && node.value.compareTo(node.children.get(0).value) < 0) {
            result = node.children.size() == 1 ? node.value : findRoot(value, node.children.get(1));
        } else {
            result = findRoot(value, node.children.get(0));
        }
        return result;
    }

    @Override
    public boolean add(E parent, E child) {
        throw new UnsupportedOperationException();
    }
}
