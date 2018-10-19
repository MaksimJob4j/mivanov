package ru.job4j.carpricespr.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import ru.job4j.carpricespr.dao.StoreException;
import ru.job4j.carpricespr.dao.UserDAO;
import ru.job4j.carpricespr.items.Car;
import ru.job4j.carpricespr.items.User;

import java.util.List;


public class UserStore extends HibStore<User> implements UserDAO {
    private final static Logger LOGGER = LogManager.getLogger(UserStore.class);

    public UserStore() {
        super(User.class);
    }

    @Override
    public User findUserByLogin(String login) throws StoreException {
        LOGGER.traceEntry();
        return HibernateUtil.tx(
                session -> {
                    Query query = session.createQuery("from User where login =\'" + login + "\'");
                    return (User) query.uniqueResult();
                }
        );
    }

    @Override
    public List<Car> findUsersCars(int userId) throws StoreException {
        LOGGER.traceEntry();
        User user = find(userId);
        return HibernateUtil.tx(
                session -> {
                    Query query = session.createQuery("from Car where owner = :owner");
                        query.setParameter("owner", user);
                    return (List<Car>) query.list();
                }
        );
    }
}