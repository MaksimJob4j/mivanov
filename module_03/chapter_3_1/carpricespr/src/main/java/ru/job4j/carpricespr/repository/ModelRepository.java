package ru.job4j.carpricespr.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Model;

import java.util.List;

@Repository
public interface ModelRepository extends EntityRepository<Model> {
    List<Model> findAllByBrandIdOrderByName(Integer brandId);
}
