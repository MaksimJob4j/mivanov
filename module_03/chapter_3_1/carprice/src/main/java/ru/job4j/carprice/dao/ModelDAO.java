package ru.job4j.carprice.dao;

import ru.job4j.carprice.items.description.Model;

import java.util.List;


public interface ModelDAO extends DAO<Model> {
    List<Model> findModelsByBrand(int brandId) throws StoreException;
}
