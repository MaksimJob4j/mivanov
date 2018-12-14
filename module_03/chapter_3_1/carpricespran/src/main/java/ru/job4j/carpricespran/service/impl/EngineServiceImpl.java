package ru.job4j.carpricespran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespran.items.description.Engine;
import ru.job4j.carpricespran.repository.EngineRepository;
import ru.job4j.carpricespran.service.EntityService;

@Service
public class EngineServiceImpl extends EntityServiceImpl<Engine> implements EntityService<Engine> {

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository) {
        super(engineRepository);
    }
}
