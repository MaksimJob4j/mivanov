package ru.job4j.crudservlet;

import org.apache.log4j.Logger;
import ru.job4j.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UserStore {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.crudservlet.UserStore.class);

    private static final UserStore INSTANCE = new UserStore();
    private UserStore() { }

    static UserStore getInstance() {
        LOGGER.debug("Вызван метод getInstance");
        return INSTANCE;
    }

    private String url;
    private String username;
    private String password;

    private Connection getConnection() throws SQLException {
        LOGGER.debug("Вызван метод");
        return DriverManager.getConnection(url, username, password);
    }

    static {
        LOGGER.debug("Вызван static блок");
        Properties prop = new Properties();
        File propFile = new File("C:\\projects\\mivanov\\crudservlet.properties");
        try (FileInputStream fileInputStream = new FileInputStream(propFile)) {
            prop.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
        INSTANCE.url = prop.getProperty("base");
        INSTANCE.username = prop.getProperty("username");
        INSTANCE.password = prop.getProperty("password");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("error", e);
        }
        LOGGER.debug("url: " + INSTANCE.url);
        LOGGER.debug("name: " + INSTANCE.username);
        LOGGER.debug("password: " + INSTANCE.password);
        INSTANCE.createUsersTable();
    }


    private void createUsersTable() {
        LOGGER.debug("Вызван метод");
        try (Connection conn = getConnection();
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

    void add(Map<String, String[]> par) {
        LOGGER.debug("Вызван метод");
        if (par.size() > 0) {
            try (PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO users (name, login, email, createDate) "
                            + "VALUES (?, ?, ?, ?)")) {
                ps.setString(1, par.get("name") == null ? "" : par.get("name") [0]);
                ps.setString(2, par.get("login") == null ? "" : par.get("login")[0]);
                ps.setString(3, par.get("email") == null ? "" : par.get("email")[0]);
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("error", e);
            }
        }
    }


    void edit(Map<String, String[]> par) {
        LOGGER.debug("Вызван метод");
        if (par.get("id") != null) {
            String idStr = par.get("id")[0];
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                for (String key : par.keySet()) {
                    if (!"id".equals(key)) {
                        editItem(id, key, par.get(key)[0]);
                    }

                }
            }
        }
    }

    private void editItem(Integer id, String fieldName, String fieldVolume) {
        LOGGER.debug("Вызван метод");
        try (PreparedStatement ps = getConnection().prepareStatement(
                "UPDATE users SET " + fieldName + " = ? WHERE id = ?")) {
            ps.setString(1, fieldVolume);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
    }

    void delete(Map<String, String[]> par) {
        LOGGER.debug("Вызван метод");
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM users");
        setWhere(par, query);
        LOGGER.debug("query: " + query);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
    }

    List<User> getUsers(Map<String, String[]> par) {
        LOGGER.debug("Вызван метод");
        List<User> result = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM users");
        setWhere(par, query);
        LOGGER.debug("query: " + query);
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query.toString())) {
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

    private void setWhere(Map<String, String[]> par, StringBuilder query) {
        LOGGER.debug("Вызван метод");
        if (par.size() > 0) {
            query.append(" WHERE ");
            for (String kye: par.keySet()) {
                query.append(kye);
                query.append(" = '");
                query.append(par.get(kye)[0]);
                query.append("' AND ");
            }
            query.delete(query.length() - 5, query.length() - 1);
        }
    }
}
