package ru.job4j.carpricespr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespr.items.description.Color;
import ru.job4j.carpricespr.repository.ColorRepository;
import ru.job4j.carpricespr.service.EntityService;

@Service
public class ColorServiceImpl extends EntityServiceImpl<Color> implements EntityService<Color> {

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository) {
        super(colorRepository);
    }
}
