package ru.job4j.carpricespr.service;

import ru.job4j.carpricespr.items.description.Model;

import java.util.List;

public interface ModelService extends EntityService<Model> {

    List<Model> findByBrandId(Integer brandId);
}
