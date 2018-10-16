package ru.job4j.storage;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class JDBCSource {
    private final static Logger LOGGER = LogManager.getLogger(JDBCSource.class);

    private BasicDataSource dataSource;

    public BasicDataSource getDataSource() throws IOException {
        LOGGER.traceEntry();
        if (this.dataSource == null) {
            BasicDataSource bds = new BasicDataSource();
            Properties prop = new Properties();
            InputStream inputStream = JDBCSource.class.
                    getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(inputStream);
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