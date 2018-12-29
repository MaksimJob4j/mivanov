package ru.job4j.carpricesprboot.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricesprboot.domain.CarParts;

public interface CarPartsService {

    @Transactional
    CarParts pickParts();
}
