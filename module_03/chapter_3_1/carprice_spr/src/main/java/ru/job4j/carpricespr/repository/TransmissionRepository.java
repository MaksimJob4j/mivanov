package ru.job4j.carpricespr.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Transmission;

@Repository
public interface TransmissionRepository extends EntityRepository<Transmission> {
}
