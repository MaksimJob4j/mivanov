package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.description.Model;
import ru.job4j.carpricesprboot.repository.ModelRepository;
import ru.job4j.carpricesprboot.service.ModelService;

import java.util.List;

@Service
public class ModelServiceImpl extends EntityServiceImpl<Model> implements ModelService {
    private final static Logger LOGGER = LogManager.getLogger(ModelServiceImpl.class);
    private final ModelRepository modelRepository;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository) {
        super(modelRepository);
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findByBrandId(Integer brandId) {
        LOGGER.traceEntry();
        return modelRepository.findAllByBrandIdOrderByName(brandId);
    }
}
