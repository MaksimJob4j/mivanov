package ru.job4j.music.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;

import java.io.IOException;
import java.io.InputStream;
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
        }
        return this.dataSource;
    }
}