package ru.job4j.carpricesprboot.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricesprboot.domain.description.Transmission;

@Repository
public interface TransmissionRepository extends EntityRepository<Transmission> {
}
