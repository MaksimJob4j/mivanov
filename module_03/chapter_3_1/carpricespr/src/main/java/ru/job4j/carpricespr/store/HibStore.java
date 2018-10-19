package ru.job4j.carpricespr.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricespr.dao.DAO;
import ru.job4j.carpricespr.dao.StoreException;

import java.util.Collection;

public class HibStore<T> implements DAO<T> {
    private static final Logger LOGGER = LogManager.getLogger(HibStore.class);

    private Class<T> type;

    public HibStore(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T item) throws StoreException {
        LOGGER.traceEntry();
        HibernateUtil.tx(session -> session.save(item));
    }

    @Override
    public Collection<T> find() throws StoreException {
        LOGGER.traceEntry();
        return HibernateUtil.tx(
                session -> session.createQuery("from " + type.getName()).list()
        );
    }

    @Override
    public T find(int id) throws StoreException {
        LOGGER.traceEntry();
        return HibernateUtil.tx(session -> session.get(type, id));
    }

    @Override
    public void update(T item) throws StoreException {
        LOGGER.traceEntry();
        HibernateUtil.tx(session -> {
            session.update(item);
            return null;
        });
    }

    @Override
    public void delete(int id) throws StoreException {
        LOGGER.traceEntry();
        HibernateUtil.tx(session -> {
            T item = session.load(type, id);
            session.delete(item);
            return null;
        });
    }

    @Override
    public void delete(T item) throws StoreException {
        LOGGER.traceEntry();
        HibernateUtil.tx(session -> {
            session.delete(item);
            return null;
        });
    }
}