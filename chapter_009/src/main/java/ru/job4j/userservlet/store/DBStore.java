package ru.job4j.userservlet.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.Role;
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
import java.util.Comparator;
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
            if (this.createRoleTable(bds) && this.createUsersTable(bds)) {
                this.dataSource = bds;
            }
            setRoles();
            setRootUser();
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
                            + "login CHARACTER VARYING (200) UNIQUE NOT NULL ,\n"
                            + "password CHARACTER VARYING (200),\n"
                            + "name CHARACTER VARYING (200),\n"
                            + "email CHARACTER VARYING (200),\n"
                            + "createDate TIMESTAMP,\n"
                            + "id_role INTEGER REFERENCES roles(id),\n"
                            + "country CHARACTER VARYING (200),\n"
                            + "city CHARACTER VARYING (200));\n"
            );
        } catch (SQLException e) {
            LOGGER.error("error", e);
            return false;
        }
        return true;
    }

    private Boolean createRoleTable(BasicDataSource bds) {
        LOGGER.traceEntry();
        try (Connection connection = bds.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS roles (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "name CHARACTER VARYING (200) UNIQUE NOT NULL ); \n"
            );
        } catch (SQLException e) {
            LOGGER.error("error", e);
            return false;
        }
        return true;
    }

    private void setRoles() {
        LOGGER.traceEntry();
        try {
            List<Role> roles = findAllRoles();
            if (roles == null || roles.size() == 0) {
                try {
                    addRole(new Role(null, "admin"));
                    addRole(new Role(null, "user"));
                } catch (UserSaveStoreException e) {
                    e.printStackTrace();
                    LOGGER.error("error", e);
                }
            }
        } catch (UserReadStoreException e) {
            e.printStackTrace();
        }
    }

    private void setRootUser() {
        LOGGER.traceEntry();
        try {
            List<User> users = findAll();
            if (users == null || users.size() == 0) {
                try {
                    add(new User(null, "root", "root", null, null, null, "admin", null, null));
                } catch (UserSaveStoreException e) {
                    e.printStackTrace();
                    LOGGER.error("error", e);
                }
            }
        } catch (UserReadStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User add(User user) throws UserSaveStoreException, UserReadStoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO users (login, password, name, email, createDate, id_role, country, city) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, Integer.parseInt(findRoleByName(user.getRole()).getId()));
            ps.setString(7, user.getCountry());
            ps.setString(8, user.getCity());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                user.setId(String.valueOf(gk.getInt("id")));
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserSaveStoreException("User save store error. " + e.getMessage());
        }
        return findById(user.getId());
    }

    @Override
    public Role addRole(Role role) throws UserSaveStoreException, UserReadStoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO roles (name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, role.getName());
            ps.executeUpdate();
            ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                role.setId(String.valueOf(gk.getInt("id")));
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserSaveStoreException("Role save store error. " + e.getMessage());
        }
        return findRoleById(role.getId());
    }

    @Override
    public User update(User user) throws UserUpdateStoreException, UserReadStoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE users SET login = ?, password = ?, name = ?, email = ?, id_role = ?, country = ?, city = ? WHERE id = ?")) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setInt(5, Integer.parseInt(findRoleByName(user.getRole()).getId()));
            ps.setString(6, user.getCountry());
            ps.setString(7, user.getCity());
            ps.setInt(8, Integer.parseInt(user.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserUpdateStoreException("User update error. " + e.getMessage());
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
            throw new UserDeleteStoreException("User delete error. " + e.getMessage());
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
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getTimestamp("createDate").toLocalDateTime(),
                                findRoleById(rs.getString("id_role")).getName(),
                                rs.getString("country"),
                                rs.getString("city")
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserReadStoreException("User read error.");
        }
        result.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
            }
        });
        return result;
    }

    @Override
    public List<Role> findAllRoles() throws UserReadStoreException {
        LOGGER.traceEntry();
        List<Role> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM roles")) {
            while (rs.next()) {
                result.add(
                        new Role(
                                rs.getString("id"),
                                rs.getString("name")
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserReadStoreException("User read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public User findById(String id) throws UserReadStoreException {
        LOGGER.traceEntry();
        return findFirst("id", id);
    }

    @Override
    public User findByLogin(String login) throws UserReadStoreException {
        LOGGER.traceEntry();
        return findFirst("login", login);
    }

    private User findFirst(String fieldName, String fieldVolume) throws UserReadStoreException {
        LOGGER.traceEntry();
        User result = null;
        String query = "SELECT * FROM users WHERE " + fieldName + " = '" + fieldVolume + "'";
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = new User(
                        rs.getString("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        findRoleById(rs.getString("id_role")).getName(),
                        rs.getString("country"),
                        rs.getString("city")
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserReadStoreException("User read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public Role findRoleById(String id) throws UserReadStoreException {
        LOGGER.traceEntry();
        return findFirstRole("id", id);
    }

    @Override
    public Role findRoleByName(String name) throws UserReadStoreException {
        LOGGER.traceEntry();
        return findFirstRole("name", name);
    }

    private Role findFirstRole(String fieldName, String fieldVolume) throws UserReadStoreException {
        LOGGER.traceEntry();
        Role result = null;
        String query = "SELECT * FROM roles WHERE " + fieldName + " = '" + fieldVolume + "'";
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = new Role(
                        rs.getString("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new UserReadStoreException("Role read error. " + e.getMessage());
        }
        return result;
    }
}