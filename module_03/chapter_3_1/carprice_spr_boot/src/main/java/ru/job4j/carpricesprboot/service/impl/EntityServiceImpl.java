package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricesprboot.repository.EntityRepository;
import ru.job4j.carpricesprboot.service.EntityService;

import java.util.List;
import java.util.Optional;

public class EntityServiceImpl<T> implements EntityService<T> {

    private final static Logger LOGGER = LogManager.getLogger(EntityServiceImpl.class);
    private final EntityRepository<T> entityRepository;

    public EntityServiceImpl(EntityRepository<T> entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public void create(T item) {
        LOGGER.traceEntry();
        entityRepository.save(item);
    }

    @Override
    public Iterable<T> find() {
        LOGGER.traceEntry();
        return entityRepository.findAll();
    }

    @Override
    public List<T> findOrdered() {
        LOGGER.traceEntry();
//        return entityRepository.findAllByOrderByNameAsc();
        return entityRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Optional<T> find(int id) {
        LOGGER.traceEntry();
        return entityRepository.findById(id);
    }

    @Override
    public void update(T item) {
        LOGGER.traceEntry();
        entityRepository.save(item);
    }

    @Override
    public void delete(int id) {
        LOGGER.traceEntry();
        entityRepository.deleteById(id);
    }

    @Override
    public void delete(T item) {
        LOGGER.traceEntry();
        entityRepository.delete(item);
    }
}
