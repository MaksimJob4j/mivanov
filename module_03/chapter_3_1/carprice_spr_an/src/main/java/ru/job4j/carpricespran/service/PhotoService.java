package ru.job4j.carpricespran.service;

import ru.job4j.carpricespran.items.description.Photo;

import java.util.List;

public interface PhotoService extends EntityService<Photo> {

    List<Photo> findByCar(Integer carId);
}
