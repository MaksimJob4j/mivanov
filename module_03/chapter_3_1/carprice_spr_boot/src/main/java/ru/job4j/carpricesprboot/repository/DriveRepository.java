package ru.job4j.carpricesprboot.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.carpricesprboot.domain.description.Drive;

@Repository
public interface DriveRepository extends EntityRepository<Drive> {
}
