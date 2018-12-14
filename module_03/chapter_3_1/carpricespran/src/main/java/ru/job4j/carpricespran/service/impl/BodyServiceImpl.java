package ru.job4j.carpricespran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespran.items.description.Body;
import ru.job4j.carpricespran.repository.BodyRepository;
import ru.job4j.carpricespran.service.EntityService;

@Service
public class BodyServiceImpl extends EntityServiceImpl<Body> implements EntityService<Body> {

    @Autowired
    public BodyServiceImpl(BodyRepository bodyRepository) {
        super(bodyRepository);
    }
}
