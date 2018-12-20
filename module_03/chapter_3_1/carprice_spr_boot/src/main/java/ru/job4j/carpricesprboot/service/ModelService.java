package ru.job4j.carpricesprboot.service;

import ru.job4j.carpricesprboot.items.description.Model;

import java.util.List;

public interface ModelService extends EntityService<Model> {

    List<Model> findByBrandId(Integer brandId);
}
