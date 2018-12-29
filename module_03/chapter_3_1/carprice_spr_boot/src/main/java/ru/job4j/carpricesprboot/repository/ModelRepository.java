package ru.job4j.carpricesprboot.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricesprboot.domain.description.Model;

import java.util.List;

@Repository
public interface ModelRepository extends EntityRepository<Model> {
    List<Model> findAllByBrandIdOrderByName(Integer brandId);
}
