package ru.job4j.carpricespran.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespran.items.description.Model;

import java.util.List;

@Repository
public interface ModelRepository extends EntityRepository<Model> {
    List<Model> findAllByBrandIdOrderByName(Integer brandId);
}
