package ru.job4j.carpricespr.dao;

import ru.job4j.carpricespr.items.description.Model;

import java.util.List;


public interface ModelDAO extends DAO<Model> {
    List<Model> findModelsByBrand(int brandId) throws StoreException;
}
