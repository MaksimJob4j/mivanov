package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.CarParts;
import ru.job4j.carpricesprboot.domain.description.*;
import ru.job4j.carpricesprboot.service.CarPartsService;
import ru.job4j.carpricesprboot.service.EntityService;

@Service
public class CarPartsServiceImpl implements CarPartsService {
    private final static Logger LOGGER = LogManager.getLogger(CarPartsServiceImpl.class);

    private final EntityService<Body> bodyService;
    private final EntityService<Brand> brandService;
    private final EntityService<Color> colorService;
    private final EntityService<Drive> driveService;
    private final EntityService<Engine> engineService;
    private final EntityService<Transmission> transmissionService;

    @Autowired
    public CarPartsServiceImpl(EntityService<Body> bodyService,
                               EntityService<Brand> brandService,
                               EntityService<Color> colorService,
                               EntityService<Drive> driveService,
                               EntityService<Engine> engineService,
                               EntityService<Transmission> transmissionService) {
        this.bodyService = bodyService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.driveService = driveService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
    }

    @Override
    public CarParts pickParts() {
        LOGGER.traceEntry();

        return new CarParts(this.bodyService.findOrdered(),
                this.brandService.findOrdered(),
                this.colorService.findOrdered(),
                this.driveService.findOrdered(),
                this.engineService.findOrdered(),
                this.transmissionService.findOrdered());
    }
}
