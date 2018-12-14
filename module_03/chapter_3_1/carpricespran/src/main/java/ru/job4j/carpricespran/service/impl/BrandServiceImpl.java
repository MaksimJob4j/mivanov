package ru.job4j.carpricespran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricespran.items.description.Brand;
import ru.job4j.carpricespran.repository.BrandRepository;
import ru.job4j.carpricespran.service.EntityService;

@Service
public class BrandServiceImpl extends EntityServiceImpl<Brand> implements EntityService<Brand> {

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        super(brandRepository);
    }



}
