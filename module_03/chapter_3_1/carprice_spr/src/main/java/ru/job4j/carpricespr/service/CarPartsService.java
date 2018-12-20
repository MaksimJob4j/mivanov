package ru.job4j.carpricespr.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricespr.items.CarParts;

public interface CarPartsService {

    @Transactional
    CarParts pickParts();
}
