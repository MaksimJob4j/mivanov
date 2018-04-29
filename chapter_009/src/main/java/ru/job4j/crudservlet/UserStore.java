package ru.job4j.crudservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UserStore {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.crudservlet.CrudServlet.class);

    private static final UserStore INSTANCE = new UserStore();

    private String username;
    private String password;
    private String url;

    private UserStore() { }

    static UserStore getInstance() {
        LOGGER.traceEntry();
        return INSTANCE;
    }

    static {
        LOGGER.traceEntry();
        Properties prop = new Properties();
        try {

            InputStream inputStream = UserStore.class.getClassLoader().getResourceAsStream("crudservlet.properties");
            prop.load(inputStream);
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
        LOGGER.traceExit();
    }

    private Connection getConnection() throws SQLException {
        LOGGER.traceEntry();
        return DriverManager.getConnection(url, username, password);
    }

    private void createUsersTable() {
        LOGGER.traceEntry();
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
        LOGGER.traceEntry();
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
        LOGGER.traceEntry();
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
        LOGGER.traceEntry();
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
        LOGGER.traceEntry();
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
        LOGGER.traceEntry();
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
        LOGGER.traceEntry();
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