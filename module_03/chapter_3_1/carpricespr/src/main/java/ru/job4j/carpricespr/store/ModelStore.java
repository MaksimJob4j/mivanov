package ru.job4j.carpricespr.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import ru.job4j.carpricespr.dao.ModelDAO;
import ru.job4j.carpricespr.dao.StoreException;
import ru.job4j.carpricespr.items.description.Brand;
import ru.job4j.carpricespr.items.description.Model;

import java.util.List;


public class ModelStore extends HibStore<Model> implements ModelDAO {
    private final static Logger LOGGER = LogManager.getLogger(ModelStore.class);

    public ModelStore() {
        super(Model.class);
    }

    @Override
    public List<Model> findModelsByBrand(int brandId) throws StoreException {
        LOGGER.traceEntry();
        return HibernateUtil.tx(
                session -> {
                    Brand brand = session.get(Brand.class, brandId);
                    Query query = session.createQuery("from Model where brand = :brand");
                    query.setParameter("brand", brand);
                    return (List<Model>) query.list();
                }
        );
    }
}