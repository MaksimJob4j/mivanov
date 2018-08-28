package ru.job4j.carprice.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import ru.job4j.carprice.dao.PhotoDAO;
import ru.job4j.carprice.dao.StoreException;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.description.Photo;

import java.util.List;


public class PhotoStore extends HibStore<Photo> implements PhotoDAO {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.store.PhotoStore.class);

    public PhotoStore() {
        super(Photo.class);
    }

    @Override
    public List<Photo> findPhotoByCar(Integer carId) throws StoreException {
        LOGGER.traceEntry();
        return HibernateUtil.tx(
                session -> {
                    Query carQuery = session.createQuery("from Car where id = :id");
                    carQuery.setParameter("id", carId);
                    Car car = (Car) carQuery.uniqueResult();
                    Query query = session.createQuery("from Photo where car = :car");
                    query.setParameter("car", car);
                    return (List<Photo>) query.list();
                }
        );
    }
}