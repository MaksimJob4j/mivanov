package ru.job4j.carpricesprboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.description.Brand;
import ru.job4j.carpricesprboot.repository.BrandRepository;
import ru.job4j.carpricesprboot.service.EntityService;

@Service
public class BrandServiceImpl extends EntityServiceImpl<Brand> implements EntityService<Brand> {

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        super(brandRepository);
    }



}
