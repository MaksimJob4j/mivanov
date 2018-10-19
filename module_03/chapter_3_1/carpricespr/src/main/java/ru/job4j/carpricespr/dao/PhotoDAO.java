package ru.job4j.carpricespr.dao;

import ru.job4j.carpricespr.items.description.Photo;

import java.util.List;


public interface PhotoDAO extends DAO<Photo> {
    List<Photo> findPhotoByCar(Integer carId) throws StoreException;
}
