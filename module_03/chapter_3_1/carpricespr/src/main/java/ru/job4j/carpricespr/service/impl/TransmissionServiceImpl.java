package ru.job4j.carpricespr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespr.items.description.Transmission;
import ru.job4j.carpricespr.repository.TransmissionRepository;
import ru.job4j.carpricespr.service.EntityService;

@Service
public class TransmissionServiceImpl extends EntityServiceImpl<Transmission> implements EntityService<Transmission> {

    @Autowired
    public TransmissionServiceImpl(TransmissionRepository transmissionRepository) {
        super(transmissionRepository);
    }
}
