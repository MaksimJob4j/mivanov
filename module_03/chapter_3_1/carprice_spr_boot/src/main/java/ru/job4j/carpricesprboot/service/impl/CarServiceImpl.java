package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.repository.exceptions.NoCarException;
import ru.job4j.carpricesprboot.domain.Car;
import ru.job4j.carpricesprboot.repository.CarRepository;
import ru.job4j.carpricesprboot.service.CarService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final static Logger LOGGER = LogManager.getLogger(CarServiceImpl.class);
    private final CarRepository carRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void create(Car car) {
        LOGGER.traceEntry();
        carRepository.save(car);
    }

    @Override
    public Iterable<Car> find() {
        LOGGER.traceEntry();
        return carRepository.findAll();
    }

    @Override
    public List<Car> findOrdered() {
        LOGGER.traceEntry();
        return carRepository.findAllByOrderByDateCreatedDesc();
    }

    @Override
    public Optional<Car> find(int id) {
        LOGGER.traceEntry();
        return carRepository.findById(id);
    }

    @Override
    public void update(Car car) {
        LOGGER.traceEntry();
        carRepository.save(car);
    }

    @Override
    public void delete(int id) {
        LOGGER.traceEntry();
        carRepository.deleteById(id);
    }

    @Override
    public void delete(Car car) {
        LOGGER.traceEntry();
        carRepository.delete(car);
    }

    @Override
    public Car findNotNull(int id) throws NoCarException {
        LOGGER.traceEntry();
        return carRepository.findById(id).orElseThrow(() -> new NoCarException(id));
    }

    @Override
    public List<Car> findByUser(Integer userId) {
        LOGGER.traceEntry();
        return carRepository.findAllByOwnerIdOrderByDateCreatedDesc(userId);
    }

    @Override
    public List<Car> findByUserLogin(String login) {
        LOGGER.traceEntry();
        return carRepository.findAllByOwnerLoginOrderByDateCreatedDesc(login);
    }

    @Override
    public List<Car> findCars(Integer brandId, Boolean dateFilter, Boolean photoFilter) {
        LOGGER.traceEntry();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
        Root<Car> root = criteria.from(Car.class);
        criteria.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (brandId > 0) {
            predicates.add(cb.equal(root.get("model").get("brand").get("id"), brandId));
        }
        if (dateFilter) {
            ZonedDateTime lastDate = carRepository.findTopByOrderByDateCreatedDesc().getDateCreated();
            if (lastDate != null) {
                lastDate = lastDate.with(LocalTime.MIDNIGHT);
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), lastDate));
            }
        }
        if (photoFilter) {
            root.fetch("photos");
            predicates.add(cb.ge(cb.size(root.get("photos")), 0));
        }
        if (!predicates.isEmpty()) {
            criteria.where(predicates.toArray(new Predicate[0]));
        }
        criteria.orderBy(cb.desc(root.get("dateCreated")));
        return em.createQuery(criteria).getResultList();
    }
}
