package ru.job4j.music.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

class DBStore {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.store.DBStore.class);

    private static final DBStore INSTANCE = new DBStore();

    private BasicDataSource dataSource;

    private DBStore() {
    }

    static DBStore getInstance() {
        return INSTANCE;
    }

    BasicDataSource getDataSource() throws StoreException {
        LOGGER.traceEntry();
        if (this.dataSource == null) {
            BasicDataSource bds = new BasicDataSource();
            Properties prop = new Properties();
            try {
                InputStream inputStream = DBStore.class.
                        getClassLoader().getResourceAsStream("music.properties");
                prop.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("error", e);
                throw new StoreException("Read properties file ERROR" + e.getMessage());
            }
            bds.setDriverClassName(prop.getProperty("driver"));
            bds.setUrl(prop.getProperty("base"));
            bds.setUsername(prop.getProperty("username"));
            bds.setPassword(prop.getProperty("password"));
            bds.setMinIdle(5);
            bds.setMaxIdle(10);
            bds.setMaxOpenPreparedStatements(100);
            this.dataSource = bds;
            createTables();
            setRoles();
            setRoot();
        }
        return this.dataSource;
    }

    private void createTables() throws StoreException {
        LOGGER.traceEntry();

        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS roles (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "name CHARACTER VARYING (200) UNIQUE NOT NULL, \n"
                            + "level SMALLINT); \n"
                            + "CREATE TABLE IF NOT EXISTS addresses (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "country CHARACTER VARYING (200), \n"
                            + "city CHARACTER VARYING (200), \n"
                            + "rest CHARACTER VARYING (200)); \n"
                            + "CREATE TABLE IF NOT EXISTS users (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "login CHARACTER VARYING (200) UNIQUE NOT NULL,\n"
                            + "password CHARACTER VARYING (200) NOT NULL,\n"
                            + "id_role INTEGER REFERENCES roles(id),\n"
                            + "id_address INTEGER REFERENCES addresses(id));\n"
                            + "CREATE TABLE IF NOT EXISTS music (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "name CHARACTER VARYING (200) UNIQUE NOT NULL ); \n"
                            + "CREATE TABLE IF NOT EXISTS music_pref (\n"
                            + "id_user INTEGER REFERENCES users(id),\n"
                            + "id_music INTEGER REFERENCES music(id), \n"
                            + "PRIMARY KEY (id_user, id_music));"
            );
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Create table error." + e.getMessage());
        }
    }

    private void setRolesFn() throws StoreException {
        LOGGER.traceEntry();

        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE OR REPLACE FUNCTION set_roles ()\n"
                            + "  RETURNS void AS $$\n"
                            + "BEGIN\n"
                            + "  IF NOT EXISTS (SELECT * FROM roles) THEN\n"
                            + "    INSERT INTO roles (name, level) VALUES ('ADMIN', 10), ('MANDATOR', 5), ('USER', 0);\n"
                            + "  END IF;\n"
                            + "END\n"
                            + "$$ LANGUAGE plpgsql;\n"
            );
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Set role error." + e.getMessage());
        }

        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT set_roles();\n");) {
            rs.next();
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Set role error." + e.getMessage());
        }
    }

    private void setRoles() throws StoreException {
        LOGGER.traceEntry();

        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM roles;\n");
             Statement setStatement = connection.createStatement()) {
            if (!resultSet.next()) {
                setStatement.executeUpdate("INSERT INTO roles (name, level) "
                        + "VALUES ('ADMIN', 10), ('MANDATOR', 5), ('USER', 0);\n");
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Set role error." + e.getMessage());
        }
    }

    private void setRoot() throws StoreException {
        LOGGER.traceEntry();

        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users;\n");
             Statement setStatement = connection.createStatement()) {
            if (!resultSet.next()) {
                setStatement.executeUpdate("INSERT INTO users (login, password, id_role) "
                        + "VALUES "
                        + "    ('root', 'root', (SELECT id FROM roles WHERE name='ADMIN')),\n"
                        + "    ('mandator', 'mandator', (SELECT id FROM roles WHERE name='MANDATOR')),\n"
                        + "    ('user', 'user', (SELECT id FROM roles WHERE name='USER'));\n");
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Set root user error." + e.getMessage());
        }
    }

    private void setMusicTypes() throws StoreException {
        LOGGER.traceEntry();

        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM music;\n");
             Statement setStatement = connection.createStatement()) {
            if (!resultSet.next()) {
                setStatement.executeUpdate("INSERT INTO music (name) "
                        + "VALUES "
                        + " ('ROCK'), ('POP'), ('JAZZ'), ('RAP'), ('CLASSIC');\n");
            }
        } catch (SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Set music type error." + e.getMessage());
        }
    }
}