package ru.job4j.carpricespran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespran.items.description.Color;
import ru.job4j.carpricespran.repository.ColorRepository;
import ru.job4j.carpricespran.service.EntityService;

@Service
public class ColorServiceImpl extends EntityServiceImpl<Color> implements EntityService<Color> {

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository) {
        super(colorRepository);
    }
}
