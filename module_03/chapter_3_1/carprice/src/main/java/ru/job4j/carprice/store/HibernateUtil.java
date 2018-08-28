package ru.job4j.carprice.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.carprice.dao.StoreException;

import java.util.function.Function;

class HibernateUtil {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.store.HibernateUtil.class);

    private static final SessionFactory SESSION_FACTORY;

    static {
        LOGGER.traceEntry();
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            LOGGER.error("error", e);
            throw e;
        }
    }

    static SessionFactory getSessionFactory() {
        LOGGER.traceEntry();
        return SESSION_FACTORY;
    }

    static <S> S tx(final Function<Session, S> command) throws StoreException {
        LOGGER.traceEntry();
        final Session session = SESSION_FACTORY.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new StoreException(e);
        } finally {
            if (tx.isActive()) {
                tx.commit();
            }
            session.close();
        }
    }
}