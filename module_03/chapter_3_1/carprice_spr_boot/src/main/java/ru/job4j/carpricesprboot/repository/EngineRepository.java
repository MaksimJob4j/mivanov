package ru.job4j.carpricesprboot.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricesprboot.items.description.Engine;

@Repository
public interface EngineRepository extends EntityRepository<Engine> {
}
