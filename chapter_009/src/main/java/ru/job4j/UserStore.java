package ru.job4j;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public enum UserStore {
    INSTANCE;

    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.UserStore.class);

    private final BasicDataSource basicDataSource = new BasicDataSource();

    UserStore() {
        init();
    }

    private void init() {
        final Logger LOGGER = LogManager.getLogger(ru.job4j.UserStore.class);

        Properties prop = new Properties();
        try {

            InputStream inputStream = ru.job4j.crudservlet.UserStore.class.
                    getClassLoader().getResourceAsStream("userservlet.properties");
            prop.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
        basicDataSource.setDriverClassName(prop.getProperty("driver"));
        basicDataSource.setUrl(prop.getProperty("base"));
        basicDataSource.setUsername(prop.getProperty("username"));
        basicDataSource.setPassword(prop.getProperty("password"));
        createUsersTable();
    }

    private void createUsersTable() {
        final Logger LOGGER = LogManager.getLogger(ru.job4j.UserStore.class);
        LOGGER.traceEntry();
        try (Connection conn = basicDataSource.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "name CHARACTER VARYING (200),\n"
                            + "login CHARACTER VARYING (200),\n"
                            + "email CHARACTER VARYING (200),\n"
                            + "createDate TIMESTAMP); \n"
            );
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
    }

    public void addUser(String name, String login, String email) {
        LOGGER.traceEntry();
        try (Connection conn = basicDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (name, login, email, createDate) "
                        + "VALUES (?, ?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, login);
            ps.setString(3, email);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
    }


    public void editUser(User user) {
        LOGGER.traceEntry();
        try (Connection conn = basicDataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET name = ?, login = ?, email = ? WHERE id = ?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setInt(4, Integer.parseInt(user.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }

    }

    public void deleteUser(String id) {
        LOGGER.traceEntry();
        try (Connection conn = basicDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
    }

    public List<User> getUsers() {
        LOGGER.traceEntry();
        List<User> result = new ArrayList<>();
        try (Connection conn = basicDataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                result.add(
                        new User(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("email"),
                                rs.getTimestamp("createDate").toLocalDateTime()
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
        return result;
    }

    public User getUser(String id) {
        LOGGER.traceEntry();
        User result = null;
        String query = "SELECT * FROM users WHERE id = " + id;
        try (Connection conn = basicDataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                result = new User(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("email"),
                                rs.getTimestamp("createDate").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
        return result;
    }
}
