package ru.job4j.todolist.dao;

import java.util.Collection;

public interface DAO<T> {
    void create(T item) throws StoreException;
    Collection<T> find() throws StoreException;
    T find(int id) throws StoreException;
    void update(T item) throws StoreException;
    void delete(int id) throws StoreException;
}
