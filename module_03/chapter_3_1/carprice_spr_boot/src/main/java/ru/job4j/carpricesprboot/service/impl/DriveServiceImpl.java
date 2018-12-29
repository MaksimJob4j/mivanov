package ru.job4j.carpricesprboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.description.Drive;
import ru.job4j.carpricesprboot.repository.DriveRepository;
import ru.job4j.carpricesprboot.service.EntityService;

@Service
public class DriveServiceImpl extends EntityServiceImpl<Drive> implements EntityService<Drive> {

    @Autowired
    public DriveServiceImpl(DriveRepository driveRepository) {
        super(driveRepository);
    }
}
