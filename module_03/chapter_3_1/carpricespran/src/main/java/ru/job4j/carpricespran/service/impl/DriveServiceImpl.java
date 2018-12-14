package ru.job4j.carpricespran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespran.items.description.Drive;
import ru.job4j.carpricespran.repository.DriveRepository;
import ru.job4j.carpricespran.service.EntityService;

@Service
public class DriveServiceImpl extends EntityServiceImpl<Drive> implements EntityService<Drive> {

    @Autowired
    public DriveServiceImpl(DriveRepository driveRepository) {
        super(driveRepository);
    }
}
