package ru.job4j.carpricesprboot.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricesprboot.domain.description.Color;

@Repository
public interface ColorRepository extends EntityRepository<Color> {
}
