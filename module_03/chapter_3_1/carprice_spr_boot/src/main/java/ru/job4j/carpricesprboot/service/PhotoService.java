package ru.job4j.carpricesprboot.service;

import ru.job4j.carpricesprboot.domain.description.Photo;

import java.util.List;

public interface PhotoService extends EntityService<Photo> {

    List<Photo> findByCar(Integer carId);
}
