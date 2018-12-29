package ru.job4j.carpricesprboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.description.Transmission;
import ru.job4j.carpricesprboot.repository.TransmissionRepository;
import ru.job4j.carpricesprboot.service.EntityService;

@Service
public class TransmissionServiceImpl extends EntityServiceImpl<Transmission> implements EntityService<Transmission> {

    @Autowired
    public TransmissionServiceImpl(TransmissionRepository transmissionRepository) {
        super(transmissionRepository);
    }
}
