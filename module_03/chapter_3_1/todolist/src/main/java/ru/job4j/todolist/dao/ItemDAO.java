package ru.job4j.todolist.dao;

public interface ItemDAO extends DAO<Item> {
    void changeDone(int id) throws StoreException;
}
