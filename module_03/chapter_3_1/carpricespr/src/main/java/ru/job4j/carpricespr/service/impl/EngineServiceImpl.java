package ru.job4j.carpricespr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespr.items.description.Engine;
import ru.job4j.carpricespr.repository.EngineRepository;
import ru.job4j.carpricespr.service.EntityService;

@Service
public class EngineServiceImpl extends EntityServiceImpl<Engine> implements EntityService<Engine> {

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository) {
        super(engineRepository);
    }
}
