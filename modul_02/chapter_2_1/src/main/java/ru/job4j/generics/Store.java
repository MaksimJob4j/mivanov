package ru.job4j.generics;

/**
 *
 * @param <T> Base.
 */
public interface Store<T extends Base> {
    /**
     *
     * @param model Base.
     * @return Base.
     */
    T add(T model);

    /**
     *
     * @param model Base.
     * @return Base.
     */
    T update(T model);

    /**
     *
     * @param id String.
     * @return Boolean.
     */
    boolean delete(String id);
}
