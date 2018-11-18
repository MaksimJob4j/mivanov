package ru.job4j.carpricespr.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Color;

@Repository
public interface ColorRepository extends EntityRepository<Color> {
}
