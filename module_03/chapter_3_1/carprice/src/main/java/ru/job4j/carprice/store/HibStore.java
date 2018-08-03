package ru.job4j.carprice.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.carprice.dao.DAO;
import ru.job4j.carprice.dao.StoreException;

import java.util.Collection;
import java.util.function.Function;

class HibStore<T> implements DAO<T> {
    private static final Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.store.HibStore.class);

    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();

    private Class<T> type;

    HibStore(Class<T> type) {
        this.type = type;
    }

    private <S> S tx(final Function<Session, S> command) throws StoreException {
        LOGGER.traceEntry();
        final Session session = SESSION_FACTORY.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw new StoreException(e);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void create(T item) throws StoreException {
        LOGGER.traceEntry();
        this.tx(session -> session.save(item));
    }

    @Override
    public Collection<T> find() throws StoreException {
        LOGGER.traceEntry();
        return this.tx(
                session -> session.createQuery("from " + type.getName()).list()
        );
    }

    @Override
    public T find(int id) throws StoreException {
        LOGGER.traceEntry();
        return this.tx(session -> session.get(type, id));
    }

    @Override
    public void update(T item) throws StoreException {
        LOGGER.traceEntry();
        this.tx(session -> {
            session.update(item);
            return null;
        });
    }

    @Override
    public void delete(int id) throws StoreException {
        this.tx(session -> {
            T item = session.load(type, id);
            session.delete(item);
            return null;
        });
    }

    @Override
    public void delete(T item) throws StoreException {
        LOGGER.traceEntry();
        this.tx(session -> {
            session.delete(item);
            return null;
        });
    }
}