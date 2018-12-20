package ru.job4j.carpricespran.service;

import ru.job4j.carpricespran.items.description.Model;

import java.util.List;

public interface ModelService extends EntityService<Model> {

    List<Model> findByBrandId(Integer brandId);
}
