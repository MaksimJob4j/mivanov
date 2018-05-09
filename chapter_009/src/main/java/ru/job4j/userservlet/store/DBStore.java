package ru.job4j.userservlet.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBStore implements Store {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.store.DBStore.class);

    private static final DBStore INSTANCE = new DBStore();

    private BasicDataSource dataSource;

    private DBStore() {
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    private BasicDataSource getDataSource() {
        LOGGER.traceEntry();
        if (this.dataSource == null) {
            BasicDataSource bds = new BasicDataSource();
            Properties prop = new Properties();
            try {
                InputStream inputStream = DBStore.class.
                        getClassLoader().getResourceAsStream("userservlet.properties");
                prop.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("error", e);
            }
            bds.setDriverClassName(prop.getProperty("driver"));
            bds.setUrl(prop.getProperty("base"));
            bds.setUsername(prop.getProperty("username"));
            bds.setPassword(prop.getProperty("password"));
            bds.setMinIdle(5);
            bds.setMaxIdle(10);
            bds.setMaxOpenPreparedStatements(100);
            if (this.createUsersTable(bds)) {
                this.dataSource = bds;
            }
        }
        return dataSource;
    }

    private Boolean createUsersTable(BasicDataSource bds) {
        LOGGER.traceEntry();
        try (Connection connection = bds.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "name CHARACTER VARYING (200),\n"
                            + "login CHARACTER VARYING (200),\n"
                            + "email CHARACTER VARYING (200),\n"
                            + "createDate TIMESTAMP); \n"
            );
        } catch (SQLException e) {
            LOGGER.error("error", e);
            return false;
        }
        return true;
    }

    @Override
    public User add(User user) throws UserSaveStoreException, UserReadStoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO users (name, login, email, createDate) "
                             + "VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            user.setCreateDate(LocalDateTime.now());
            ps.setTimestamp(4, Timestamp.valueOf(user.getCreateDate()));
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                user.setId(String.valueOf(gk.getInt("id")));
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserSaveStoreException("User save store error.");
        }
        return findById(user.getId());
    }

    @Override
    public User update(User user) throws UserUpdateStoreException, UserReadStoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE users SET name = ?, login = ?, email = ? WHERE id = ?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setInt(4, Integer.parseInt(user.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserUpdateStoreException("User update error.");
        }
        return findById(user.getId());
    }

    @Override
    public User delete(String id) throws UserDeleteStoreException, UserReadStoreException {
        LOGGER.traceEntry();
        User user = findById(id);
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserDeleteStoreException("User delete error.");
        }
        return user;
    }

    @Override
    public List<User> findAll() throws UserReadStoreException {
        LOGGER.traceEntry();
        List<User> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                result.add(
                        new User(
                                rs.getString("id"),
                                rs.getString("login"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getTimestamp("createDate").toLocalDateTime()
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserReadStoreException("User read error.");
        }
        return result;
    }

    @Override
    public User findById(String id) throws UserReadStoreException {
        LOGGER.traceEntry();
        User result = null;
        String query = "SELECT * FROM users WHERE id = " + id;
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                result = new User(
                        rs.getString("id"),
                        rs.getString("login"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("createDate").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserReadStoreException("User read error.");
        }
        return result;
    }
}