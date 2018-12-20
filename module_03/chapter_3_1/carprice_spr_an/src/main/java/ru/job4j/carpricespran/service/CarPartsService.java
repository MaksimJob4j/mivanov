package ru.job4j.carpricespran.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.carpricespran.items.CarParts;

public interface CarPartsService {

    @Transactional
    CarParts pickParts();
}
