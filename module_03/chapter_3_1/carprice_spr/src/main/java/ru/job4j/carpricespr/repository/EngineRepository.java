package ru.job4j.carpricespr.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Engine;

@Repository
public interface EngineRepository extends EntityRepository<Engine> {
}
