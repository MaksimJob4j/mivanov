package ru.job4j.carpricespr.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.carpricespr.dao.StoreException;

import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {
    private final static Logger LOGGER = LogManager.getLogger(HibernateUtil.class);

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

    private static SessionFactory getSessionFactory() {
        LOGGER.traceEntry();
        return SESSION_FACTORY;
    }

    public static <S> S tx(final Function<Session, S> command) throws StoreException {
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

    public static void ses(final Consumer<Session> command) {
        LOGGER.traceEntry();
        try (final Session session = SESSION_FACTORY.openSession()) {
            command.accept(session);
        }
    }
}