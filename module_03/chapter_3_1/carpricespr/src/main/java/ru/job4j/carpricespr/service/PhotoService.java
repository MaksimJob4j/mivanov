package ru.job4j.carpricespr.service;

import ru.job4j.carpricespr.items.description.Photo;

import java.util.List;

public interface PhotoService extends EntityService<Photo> {

    List<Photo> findByCar(Integer carId);
}
