package ru.job4j.carpricesprboot.service;

import java.util.List;
import java.util.Optional;

public interface EntityService<T> {

    void create(T item);
    Iterable<T> find();
    List<T> findOrdered();
    Optional<T> find(int id);
    void update(T item);
    void delete(int id);
    void delete(T item);
}
