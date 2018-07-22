package ru.job4j.music.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.AddressDAO;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStoreAddress implements AddressDAO {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.store.DBStoreAddress.class);

    private static final DBStoreAddress INSTANCE = new DBStoreAddress();

    private DBStoreAddress() {
    }

    public static DBStoreAddress getInstance() {
        return INSTANCE;
    }

    private BasicDataSource getDataSource() throws StoreException {
        LOGGER.traceEntry();
        try {
            return DBStore.getInstance().getDataSource();
        } catch (StoreException e) {
            LOGGER.error("error", e);
            throw new StoreException("DataBase connection error. " + e.getMessage());
        }
    }

    @Override
    public void create(Address address) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO addresses (country, city, rest) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getRestAddress());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                address.setId(gk.getInt("id"));
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Address save store error. " + e.getMessage());
        }
    }

    @Override
    public void update(Address address) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE addresses SET country=?, city=?, rest=? WHERE id = ?")) {
            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getRestAddress());
            ps.setInt(4, address.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Address update error. " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM addresses WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Address delete error. " + e.getMessage());
        }
    }

    @Override
    public List<Address> find() throws StoreException {
        LOGGER.traceEntry();
        List<Address> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM addresses")) {
            while (rs.next()) {
                result.add(
                        new Address(
                                rs.getInt("id"),
                                rs.getString("country"),
                                rs.getString("city"),
                                rs.getString("rest")
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Address read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public Address find(int id) throws StoreException {
        LOGGER.traceEntry();
        Address result = null;
        String query = "SELECT * FROM addresses WHERE id = '" + id + "'";
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = new Address(
                        rs.getInt("id"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("rest")
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Address read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public Address find(String address) throws StoreException {
        LOGGER.traceEntry();
        throw new StoreException("The method is not defined.");
    }
}