package ru.job4j.music.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.MusicTypeDAO;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.MusicType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStoreMusicType implements MusicTypeDAO {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.store.DBStoreMusicType.class);

    private static final DBStoreMusicType INSTANCE = new DBStoreMusicType();

    private DBStoreMusicType() {
    }

    public static DBStoreMusicType getInstance() {
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
    public void create(MusicType musicType) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO music (name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, musicType.getName());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                musicType.setId(gk.getInt("id"));
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Music save store error. " + e.getMessage());
        }
    }

    @Override
    public void update(MusicType musicType) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE music SET name  = ? WHERE id = ?")) {
            ps.setString(1, musicType.getName());
            ps.setInt(2, musicType.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Music update error. " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM music WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Music delete error. " + e.getMessage());
        }
    }

    @Override
    public List<MusicType> find() throws StoreException {
        LOGGER.traceEntry();
        List<MusicType> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM music")) {
            while (rs.next()) {
                result.add(
                        new MusicType(
                                rs.getInt("id"),
                                rs.getString("name")
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Music read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public MusicType find(int id) throws StoreException {
        LOGGER.traceEntry();
        return findFirstMusic("id", String.valueOf(id));
    }

    @Override
    public MusicType find(String name) throws StoreException {
        LOGGER.traceEntry();
        return findFirstMusic("name", name);
    }

    private MusicType findFirstMusic(String fieldName, String fieldVolume) throws StoreException {
        LOGGER.traceEntry();
        MusicType result = null;
        String query = "SELECT * FROM music WHERE " + fieldName + " = '" + fieldVolume + "'";
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = new MusicType(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Music read error. " + e.getMessage());
        }
        return result;
    }
}