package ru.job4j.todolist.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.todolist.dao.Item;
import ru.job4j.todolist.dao.ItemDAO;
import ru.job4j.todolist.dao.StoreException;

import java.util.Collection;
import java.util.List;

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
        Session session = SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("error", e);
			session.getTransaction().rollback();
            throw new StoreException(e.getMessage());
        } finally {
            session.close();
        }
    }
	
	@Override
    public Collection<Item> find() throws StoreException {
        LOGGER.traceEntry();
		List<Item> items = null;
		try (Session session = SESSION_FACTORY.openSession()) {
            items = session.createQuery("from Item").list();
        } catch (Exception e) {
            LOGGER.error("error", e);
            throw new StoreException(e.getMessage());
        }
		return items;
	}

	@Override
	public Item find(int id) throws StoreException {
        LOGGER.traceEntry();
		Item item = null;
		try (Session session = SESSION_FACTORY.openSession()) {
            item = (Item) session.get(Item.class, id);
        } catch (Exception e) {
            LOGGER.error("error", e);
            throw new StoreException(e.getMessage());
        }
		return item; 
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
        Session session = SESSION_FACTORY.openSession();
		try {
            session.beginTransaction();
			Item item = (Item) session.load(Item.class, id);
			item.setDone(!item.getDone());
			session.flush();
			session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("error", e);
			session.getTransaction().rollback();
            throw new StoreException(e.getMessage());
        } finally {
		    session.close();
        }
    }
}