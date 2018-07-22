package ru.job4j.music.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.RoleDAO;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStoreRole implements RoleDAO {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.store.DBStoreRole.class);

    private static final DBStoreRole INSTANCE = new DBStoreRole();

    private DBStoreRole() {
    }

    public static DBStoreRole getInstance() {
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
    public void create(Role role) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO roles (name, level) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, role.getName());
            ps.setInt(1, role.getLevel());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                role.setId(gk.getInt("id"));
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Role save store error. " + e.getMessage());
        }
    }

    @Override
    public void update(Role role) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE roles SET name = ?, level = ? WHERE id = ?")) {
            ps.setString(1, role.getName());
            ps.setInt(2, role.getLevel());
            ps.setInt(3, role.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Role update error. " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM roles WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Role delete error. " + e.getMessage());
        }
    }

    @Override
    public List<Role> find() throws StoreException {
        LOGGER.traceEntry();
        List<Role> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM roles")) {
            while (rs.next()) {
                result.add(
                        new Role(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("level")
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Role read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public Role find(int id) throws StoreException {
        LOGGER.traceEntry();
        return findFirstRole("id", String.valueOf(id));
    }

    @Override
    public Role find(String name) throws StoreException {
        LOGGER.traceEntry();
        return findFirstRole("name", name);
    }

    private Role findFirstRole(String fieldName, String fieldVolume) throws StoreException {
        LOGGER.traceEntry();
        Role result = null;
        String query = "SELECT * FROM roles WHERE " + fieldName + " = '" + fieldVolume + "'";
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = new Role(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("level")
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Role read error. " + e.getMessage());
        }
        return result;
    }
}