package ru.job4j.todolist.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.todolist.dao.Item;
import ru.job4j.todolist.dao.ItemDAO;
import ru.job4j.todolist.dao.StoreException;

import java.util.Collection;
import java.util.function.Function;

public class ItemStore implements ItemDAO {
    private static final Logger LOGGER = LogManager.getLogger(ru.job4j.todolist.store.ItemStore.class);

	private static final ItemStore INSTANCE = new ItemStore();
	
    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();
	
    private ItemStore() {
    }

    public static ItemStore getInstance() {
        return INSTANCE;
    }

	@Override
	public void create(Item item) throws StoreException {
        LOGGER.traceEntry();
        this.tx(session -> session.save(item));
    }
	
	@Override
    public Collection<Item> find() throws StoreException {
        LOGGER.traceEntry();
        return this.tx(
                session -> session.createQuery("from Item ").list()
        );
	}

	@Override
	public Item find(int id) throws StoreException {
        LOGGER.traceEntry();
        return this.tx(
                session -> session.get(Item.class, id)
        );
	}

    @Override
    public void update(Item item) throws StoreException {
	    throw new  UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public void delete(int id) throws StoreException {
        throw new  UnsupportedOperationException("Not implemented, yet");
    }

	@Override
	public void changeDone(int id) throws StoreException {
        LOGGER.traceEntry();
        this.tx(session -> {
            Item item = session.load(Item.class, id);
            item.setDone(!item.getDone());
            return null;
        });
    }

    private <T> T tx(final Function<Session, T> command) throws StoreException {
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
}