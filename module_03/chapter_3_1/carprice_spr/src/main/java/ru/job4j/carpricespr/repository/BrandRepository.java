package ru.job4j.carpricespr.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Brand;

@Repository
public interface BrandRepository extends EntityRepository<Brand> {
}
