package ru.job4j.carpricespr.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricespr.items.description.Drive;

@Repository
public interface DriveRepository extends EntityRepository<Drive> {
}
