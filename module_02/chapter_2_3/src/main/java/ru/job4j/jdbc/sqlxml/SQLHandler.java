package ru.job4j.jdbc.sqlxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class SQLHandler {
    private String url;
    private String username;
    private String password;

    SQLHandler(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    void setTable(Integer atrCount) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                createTable(conn);
                fillTable(conn, atrCount);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillTable(Connection conn, Integer atrCount) throws SQLException {
        int numberWriteCycle = 0;
        int writeCycle = 1_000;
        int value = 0;
        while (value < atrCount) {
            value = numberWriteCycle * writeCycle;
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO test (field) VALUES ");
            for (int i = 0; i < writeCycle && value < atrCount; i++) {
                sb.append(String.format("(%s),", ++value));
            }
            sb.deleteCharAt(sb.length() - 1);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate(sb.toString());
            }
            numberWriteCycle++;
        }
    }

    private void createTable(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS test (field INT);\n"
                            + "DELETE FROM test");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    EntriesIn readFromBaseToClass() {
        EntriesIn entriesIn = new EntriesIn();
        entriesIn.setEntry(new LinkedList<>());

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM test")) {
            while (rs.next()) {
                EntryIn entry = new EntryIn();
                entry.setField(rs.getInt("field"));
                entriesIn.getEntry().add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entriesIn;
    }

}
