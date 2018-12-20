package ru.job4j.carpricesprboot.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricesprboot.items.CarParts;

public interface CarPartsService {

    @Transactional
    CarParts pickParts();
}
