package ru.job4j.jdbc.jobparser;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

public class SQLHandler {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.jdbc.jobparser.SQLHandler.class);
    private String url;
    private String username;
    private String password;

    void initBase(Properties prop) {
        LOGGER.debug("Вызван метод initBase c параметром класса Properties");
        url = prop.getProperty("base");
        LOGGER.debug("url установлено значение " + url);
        username = prop.getProperty("username");
        LOGGER.debug("username установлено значение " + username);
        password = prop.getProperty("password");
        LOGGER.debug("password установлено значение " + password);
        createTables();
    }

    private Connection getConnection() throws SQLException {
        LOGGER.debug("Вызван метод getConnection");
        return DriverManager.getConnection(url, username, password);
    }

    private void createTables() {
        LOGGER.debug("Вызван метод createTables");
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS authors (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "name CHARACTER VARYING (200),\n"
                            + "link TEXT); \n"
                            + "CREATE TABLE IF NOT EXISTS offers (\n"
                            + "id serial PRIMARY KEY,\n"
                            + "topic CHARACTER VARYING (200),\n"
                            + "link TEXT,\n"
                            + "changed TIMESTAMP, \n"
                            + "closed BOOLEAN, \n"
                            + "id_author INTEGER REFERENCES authors(id)); \n"
                            + ";"
            );
            LOGGER.debug("Вызван запрос создания таблиц");
        } catch (SQLException e) {
            LOGGER.error("Ошибка " + e);
        }
    }

    Boolean saveData(List<JobOffer> offers) {
        LOGGER.info("Вызван метод saveData (запись в базу данных)");
        Boolean result = false;
        if (offers.size() != 0) {
            try (Connection conn = getConnection()) {
                conn.setAutoCommit(false);
                LOGGER.debug("setAutoCommit установлен false");
                try {
                    for (JobOffer jobOffer : offers) {
                        LOGGER.debug("Запись в базу в таблицу authors автора с name: " + jobOffer.authorName);
                        Integer authorID = saveAuthor(conn, jobOffer);
                        LOGGER.debug("Запись в базу в таблицу offers офера: " + jobOffer.jobTopic);
                        Integer offerId = saveOffer(conn, jobOffer, authorID);
                    }
                    conn.commit();
                    LOGGER.info("commit. Данные записаны в базу");
                    result = true;
                } catch (SQLException e) {
                    conn.rollback();
                    LOGGER.error("rollback. Запись отменена");
                    LOGGER.error("Ошибка " + e);
                }
            } catch (SQLException e) {
                LOGGER.error("Ошибка " + e);
            }
        } else {
            result = true;
        }
        return result;
    }

    private Integer saveOffer(Connection conn, JobOffer jobOffer, Integer authorID) {
        LOGGER.debug("Вызван метод saveOffer с параметрами: " + conn + " " +  jobOffer + " " + authorID);
        Integer offerID = null;
        try (PreparedStatement st = conn.prepareStatement("SELECT id FROM offers WHERE link = ?")) {
            st.setString(1, jobOffer.jobLink);
            ResultSet rs = st.executeQuery();
            LOGGER.debug("Поиск в базе оффера " + jobOffer.jobTopic);
            if (!rs.next()) {
                LOGGER.debug("Офер в базе не найден. Записываем в базу");
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO offers (topic, link, changed, closed, id_author) "
                                + "VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, jobOffer.jobTopic);
                ps.setString(2, jobOffer.jobLink);
                ps.setTimestamp(3, Timestamp.valueOf(jobOffer.changed));
                ps.setBoolean(4, jobOffer.closed);
                ps.setInt(5, authorID);
                ps.executeUpdate();
                ResultSet gk = ps.getGeneratedKeys();
                if (gk.next()) {
                    offerID = gk.getInt("id");
                }
                LOGGER.debug("Записан офер с id " + offerID);
                gk.close();
                ps.close();
            } else {
                offerID = rs.getInt("id");
                LOGGER.debug("Офер найден в базе, id " + offerID);
                LOGGER.debug("Обновление записи офера в базе");
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE  offers SET topic = ?, changed = ?, closed = ? "
                                + "WHERE id = ?");
                ps.setString(1, jobOffer.jobTopic);
                ps.setTimestamp(2, Timestamp.valueOf(jobOffer.changed));
                ps.setBoolean(3, jobOffer.closed);
                ps.setInt(4, offerID);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка " + e);
        }
        return offerID;
    }

    private Integer saveAuthor(Connection conn, JobOffer jobOffer) {
        LOGGER.debug("Вызван метод saveAuthor с параметрами: " + conn + " " + jobOffer);
        Integer authorID = null;
        LOGGER.debug("Поиск в базе автора " + jobOffer.authorName);
        try (PreparedStatement st = conn.prepareStatement("SELECT id FROM authors WHERE name = ?")) {
            st.setString(1, jobOffer.authorName);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                LOGGER.debug("Автор в базе не найден. Записываем в базу");
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO authors (name, link) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, jobOffer.authorName);
                ps.setString(2, jobOffer.authorLink);
                ps.executeUpdate();
                ResultSet gk = ps.getGeneratedKeys();
                if (gk.next()) {
                    authorID = gk.getInt("id");
                }
                LOGGER.debug("Записан автор с id " + authorID);

                gk.close();
                ps.close();
            } else {
                authorID = rs.getInt("id");
                LOGGER.debug("Автор найден в базе, id " + authorID);
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка " + e);
        }
        return authorID;
    }
}
