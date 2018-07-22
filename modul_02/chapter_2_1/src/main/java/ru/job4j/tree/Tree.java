package ru.job4j.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 1. Создать элементарную структуру дерева [#1711]
 * 1. Реализовать интерфейс
 * public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
 * boolean add(E parent, E child);
 * }
 * 2. Элемент дерева может иметь множество дочерних элементов.
 * class Tree<E extends Comparable<E>> implements SimpleTree<E> {
 *
 * class Node<E> {
 * List<Node<E>> childen;
 * E value;
 * }
 * public boolean add(E parent, E child)
 * public Iterator<E> iterator()
 * }
 *
 * метод add - Должен находить элемент parent в дереве по условию compare(node, parent) == 0
 * и добавлять в него дочерний элемент.
 * node.children.add(child);
 *
 * В дереве не могут быть дубликатов.
 * Итератор должен собрать все элементы в List и возвращать данные из скопированной коллекции.
 * *
 *
 * @param <E>
 */
class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    class Node<E> {
        E value;
        List<Node<E>> children;

        Node() {
        }

        Node(E value) {
            this.value = value;
        }
    }

    private Node<E> root;

    public Node<E> getRoot() {
        return root;
    }


    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        if (parent != null) {
            if (root == null) {
                if (!parent.equals(child)) {
                    root = new Node<>(parent);
                    if (child != null) {
                        root.children = new ArrayList<>();
                        root.children.add(new Node<>(child));
                    }
                    result = true;
                }
            } else if (child != null && !contains(child)) {
                Node<E> node = findNodeInBranch(parent, root);
                if (node != null) {
                    if (node.children == null) {
                        node.children = new ArrayList<>();
                    }

                    node.children.add(new Node<>(child));
                    result = true;
                }
            }
        }
        return result;
    }


    private Node<E> findNodeInBranch(E value, Node<E> node) {
        Node<E> result = null;
        if (node.value.equals(value)) {
            result = node;
        } else if (node.children != null) {
            int i = 0;
            while (i < node.children.size() && result == null) {
                result = findNodeInBranch(value, node.children.get(i++));
            }
        }
        return result;
    }

    public boolean contains(E value) {
        boolean result = false;
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            if (it.next().equals(value)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            {
                nodes = new ArrayList<>();
                collectValue(root);
            }

            private void collectValue(Node<E> node) {
                if (node != null) {
                    nodes.add(node.value);
                    if (node.children != null) {
                        for (Node<E> n : node.children) {
                            collectValue(n);
                        }
                    }
                }
            }

            List<E> nodes;
            Iterator<E> it = nodes.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return it.next();
            }
        };
    }

    /**
     * Метод должен проверять количество дочерних элементов в дереве.
     * Если их <= 2 - то дерево бинарное.
     *
     * @return true если дерево бинарное.
     */
    public boolean isBinary() {
        return isBinary(root);
    }

    /**
     * Проверяет ветвь на бинарность.
     *
     * @param node корень ветви для проверки.
     * @return true если ветвь бинарная.
     */
    private boolean isBinary(Node<E> node) {
        boolean result;
        if (node == null || node.children == null || node.children.size() == 0) {
            result = true;
        } else if (node.children.size() > 2) {
            result = false;
        } else {
            result =   (isBinary(node.children.get(0))
                    && (node.children.size() == 1
                    || isBinary(node.children.get(1))));
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<E> it = iterator();

        sb.append("Tree{");

        while (it.hasNext()) {
            E v = it.next();
            sb.append("{V:");
            sb.append(v);
            sb.append(", children:");
            List<Node<E>> nodes = findNodeInBranch(v, root).children;
            if (nodes != null) {
                for (Node<E> node: nodes) {
                    sb.append(node.value);
                    sb.append(", ");
                }
            }
            try {
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.deleteCharAt(sb.lastIndexOf(" "));
            } catch (Exception e) { }

            sb.append("}, ");
        }
        try {
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.deleteCharAt(sb.lastIndexOf(" "));
        } catch (Exception e) { }

        sb.append("}");

        return sb.toString();
    }
}