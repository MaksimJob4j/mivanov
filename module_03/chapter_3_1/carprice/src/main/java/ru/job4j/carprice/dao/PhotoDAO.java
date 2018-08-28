package ru.job4j.carprice.dao;

import ru.job4j.carprice.items.description.Photo;

import java.util.List;


public interface PhotoDAO extends DAO<Photo> {
    public List<Photo> findPhotoByCar(Integer carId) throws StoreException;
}
