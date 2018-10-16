package ru.job4j.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcStorage implements Storage {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.storage.JdbcStorage.class);

    private final JDBCSource jdbcSource;

    public JdbcStorage(JDBCSource jdbcSource) {
        this.jdbcSource = jdbcSource;
        initBase();
    }

    private void initBase() {
        LOGGER.traceEntry();
        try (Statement statement = this.jdbcSource.getDataSource().getConnection().createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR (255));"
            );
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void create(User user) {
        LOGGER.traceEntry();
        try (Connection connection = this.jdbcSource.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO users (login) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                user.setId(gk.getInt("id"));
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        LOGGER.traceEntry();
        try (Connection connection = this.jdbcSource.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE users SET login=? WHERE id = ?")) {
            ps.setString(1, user.getLogin());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        LOGGER.traceEntry();
        try (Connection connection = this.jdbcSource.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM users WHERE id = (?)")) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        LOGGER.traceEntry();
        User result = null;
        String query = "SELECT * FROM users WHERE id = '" + id + "'";
        try (Connection connection = this.jdbcSource.getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = getUserFromResultSet(rs);
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> find() {
        LOGGER.traceEntry();
        List<User> result = new ArrayList<>();
        try (Connection connection = this.jdbcSource.getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                result.add(getUserFromResultSet(rs));
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
        return result;
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        LOGGER.traceEntry();
        return new User(
                rs.getInt("id"),
                rs.getString("login")
        );
    }
}