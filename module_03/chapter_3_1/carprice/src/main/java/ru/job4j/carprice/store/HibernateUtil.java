package ru.job4j.carprice.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
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
}