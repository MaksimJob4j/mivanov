package ru.job4j.carpricespran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespran.items.description.Transmission;
import ru.job4j.carpricespran.repository.TransmissionRepository;
import ru.job4j.carpricespran.service.EntityService;

@Service
public class TransmissionServiceImpl extends EntityServiceImpl<Transmission> implements EntityService<Transmission> {

    @Autowired
    public TransmissionServiceImpl(TransmissionRepository transmissionRepository) {
        super(transmissionRepository);
    }
}
