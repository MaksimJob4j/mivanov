package ru.job4j.generics;

import java.util.Iterator;

/**
 * AbstractStore.
 * @param <T> Класс.
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    /**
     * SimpleArray.
     */
    private SimpleArray<T> simpleArray = new SimpleArray<>();

    @Override
    public T add(T model) {
        simpleArray.add(model);
        return model;
    }

    @Override
    public T update(T model) {
        simpleArray.update(model);
        return model;
    }

    @Override
    public boolean delete(String id) {
        Boolean isDeleted = false;
        Iterator<T> it = simpleArray.iterator();

        while (it.hasNext()) {
            if (id.equals(it.next().getId())) {
                it.remove();
                isDeleted = true;
            }
        }

        return isDeleted;
    }

    /**
     * get.
     * @param i int.
     * @return T.
     */
    public T get(int i) {
        return simpleArray.get(i);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");
        for (T t: simpleArray) {
            stringBuilder.append(t.getId());
            stringBuilder.append(" ");
        }
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
