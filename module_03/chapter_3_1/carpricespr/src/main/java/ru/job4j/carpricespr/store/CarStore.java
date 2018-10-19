package ru.job4j.carpricespr.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.job4j.carpricespr.items.description.*;
import ru.job4j.carpricespr.dao.CarDAO;
import ru.job4j.carpricespr.dao.StoreException;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.items.User;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;


public class CarStore extends HibStore<Car> implements CarDAO {
    private final static Logger LOGGER = LogManager.getLogger(CarStore.class);

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