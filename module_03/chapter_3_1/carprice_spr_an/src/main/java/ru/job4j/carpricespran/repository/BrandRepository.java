package ru.job4j.carpricespran.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespran.items.description.Brand;

@Repository
public interface BrandRepository extends EntityRepository<Brand> {
}
