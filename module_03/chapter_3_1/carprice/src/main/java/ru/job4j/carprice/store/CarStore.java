package ru.job4j.carprice.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.job4j.carprice.dao.CarDAO;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;
import ru.job4j.carprice.items.description.*;

import javax.persistence.criteria.*;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CarStore extends HibStore<Car> implements CarDAO {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.store.CarStore.class);

    public CarStore() {
        super(Car.class);
    }

    @Override
    public List<Car> findCars(Integer brandFilter, Boolean dateFilter, Boolean photoFilter) throws StoreException {
        LOGGER.traceEntry();

        return HibernateUtil.tx(
                session -> {
                    boolean isFilter = false;
                    String textQuery = "from Car as c";
                    if (brandFilter > 0) {
                        textQuery += " where";
                        isFilter = true;
                        textQuery += " c.model.brand.id  = " + brandFilter;
                    }
                    ZonedDateTime lastDate = null;
                    if (dateFilter) {
                        Query lastDayQuery = session.createQuery(
                                "select max(dateCreated) from Car");
                        lastDate = (ZonedDateTime) lastDayQuery.uniqueResult();
                        if (lastDate != null) {
                            textQuery += isFilter ? " and" : " where";
                            isFilter = true;
                            lastDate = lastDate.with(LocalTime.MIDNIGHT);
                            textQuery += " c.dateCreated > :date";
                        }
                    }
                    if (photoFilter) {
                        textQuery += isFilter ? " and" : " where";
                        textQuery += " c.id = some ( select p.car.id from Photo as p)";
                    }
                    textQuery += " order by c.dateCreated desc";
                    Query query = session.createQuery(textQuery);
                    if (lastDate != null) {
                        query.setParameter("date", lastDate);
                    }
                    return (List<Car>) query.list();
                }
        );
    }

    @Override
    public List<Car> findCarsCriteria(Integer brandFilter, Boolean dateFilter, Boolean photoFilter) throws StoreException {
        LOGGER.traceEntry();

        return HibernateUtil.tx(
                session -> {
                    CriteriaBuilder cb = session.getCriteriaBuilder();
                    CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
                    Root<Car> root = criteria.from(Car.class);
                    criteria.select(root);
                    List<Predicate> predicates = new ArrayList<>();
                    if (brandFilter > 0) {
                        predicates.add(cb.equal(root.get("model").get("brand").get("id"), brandFilter));
                    }
                    if (dateFilter) {
                        Query lastDayQuery = session.createQuery(
                                "select max(dateCreated) from Car");
                        ZonedDateTime lastDate = (ZonedDateTime) lastDayQuery.uniqueResult();
                        if (lastDate != null) {
                            lastDate = lastDate.with(LocalTime.MIDNIGHT);
                            predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), lastDate));
                        }
                    }
                    if (photoFilter) {
                        Query carsQuery = session.createQuery("select (car.id) from Photo");
                        List<Car> carsId = (List<Car>) carsQuery.list();
                        Expression<String> parentExpression = root.get("id");
                        predicates.add(parentExpression.in(carsId));
                    }
                    if (!predicates.isEmpty()) {
                        criteria.where(predicates.toArray(new Predicate[0]));
                    }
                    criteria.orderBy(cb.desc(root.get("dateCreated")));
                    return session.createQuery(criteria).getResultList();
                }
        );
    }

    @Override
    public Car createCarFromParameters(Map<String, String> parameters, User owner) throws StoreException {
        LOGGER.traceEntry();

        return HibernateUtil.tx(
                session -> {
                    Car car = new Car();
                    car.setModel((Model) findEntity(parameters.get("model"), Model.class, session));
                    car.setYear(parsInt(parameters.get("year")));
                    car.setMileage(parsInt(parameters.get("mileage")));
                    car.setBody((Body) findEntity(parameters.get("body"), Body.class, session));
                    car.setColor((Color) findEntity(parameters.get("color"), Color.class, session));
                    car.setVolume(parsFloat(parameters.get("volume")));
                    car.setTransmission((Transmission) findEntity(parameters.get("transmission"), Transmission.class, session));
                    car.setEngine((Engine) findEntity(parameters.get("engine"), Engine.class, session));
                    car.setDrive((Drive) findEntity(parameters.get("drive"), Drive.class, session));
                    car.setRightWheel(Boolean.parseBoolean(parameters.get("rightWheel")));
                    car.setBroken(Boolean.parseBoolean(parameters.get("broken")));
                    car.setOwnersNum(parsInt(parameters.get("ownersNum")));
                    car.setVin(parameters.get("vin"));
                    car.setPower(parsInt(parameters.get("power")));
                    car.setAddress(parameters.get("address"));
                    car.setOwner(owner);
                    car.setPrice(parsInt(parameters.get("price")));
                    car.setDateCreated(ZonedDateTime.now());
                    session.save(car);
                    return car;
                }
        );
    }

    private Float parsFloat(String stringPar) {
        LOGGER.traceEntry();
        if (notNull(stringPar)) {
            return Float.parseFloat(stringPar);
        }
        return null;
    }

    private Integer parsInt(String stringPar) {
        LOGGER.traceEntry();
        if (notNull(stringPar)) {
            return Integer.parseInt(stringPar);
        }
        return null;
    }

    private Object findEntity(String id, Class entityClass, Session session) {
        LOGGER.traceEntry();
        if (notNull(id)) {
            return session.find(entityClass, Integer.parseInt(id));
        }
        return null;
    }

    private boolean notNull(String stringPar) {
        return stringPar != null && !"".equals(stringPar);
    }
}