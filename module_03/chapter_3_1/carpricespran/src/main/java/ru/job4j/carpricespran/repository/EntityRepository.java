package ru.job4j.carpricespran.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface EntityRepository<T> extends CrudRepository<T, Integer> {
    List<T> findAllByOrderByNameAsc();
}
