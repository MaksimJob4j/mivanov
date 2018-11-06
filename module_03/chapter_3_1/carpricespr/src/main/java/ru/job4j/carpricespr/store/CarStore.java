package ru.job4j.carpricespr.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.job4j.carpricespr.dao.CarDAO;
import ru.job4j.carpricespr.dao.StoreException;
import ru.job4j.carpricespr.items.Car;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;


@Component
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
}