package ru.job4j.start;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Tracker.
 */
public class Tracker {
    private File initFile;
    private BDTracker bd;

    Tracker(File initFile) {
        this.initFile = initFile;
    }

    void init() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BDTracker.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            bd = (BDTracker) jaxbUnmarshaller.unmarshal(initFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
             Statement st = conn.createStatement()) {
            st.executeUpdate(bd.sqlCreateTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void clearBase() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BDTracker.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            bd = (BDTracker) jaxbUnmarshaller.unmarshal(initFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
             Statement st = conn.createStatement()) {
            st.executeUpdate(bd.sqlClearBase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * добавление заявок.
     * @param item item.
     */
    void add(Item item) {
        if (item != null) {
            ArrayList<Item> items = findByName(item.getName());
            for (Item it: items) {
                if (item.equalsWithoutId(it)) {
                    item.setID(it.getId());
                    return;
                }
            }
            try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
                 PreparedStatement st = conn.prepareStatement(bd.sqlAddItem, Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, item.getName());
                st.setString(2, item.getTask());
                st.setLong(3, item.getCreated());
                st.executeUpdate();
                ResultSet gk = st.getGeneratedKeys();
                if (gk.next()) {
                    item.setID(gk.getInt("id"));
                }
                gk.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Удаление заявок.
     * @param item item.
     */
    void delete(Item item) {
        if (item.getId() != null) {
            try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
                 PreparedStatement st = conn.prepareStatement(bd.sqlRemoveItem)) {
                st.setInt(1, item.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Получение списка всех заявок.
     * @return items.
     */
    ArrayList<Item> findAll() {
        ArrayList<Item> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(bd.sqlFindAll)) {
            while (rs.next()) {
                Item item = new Item(
                        rs.getString("name"),
                        rs.getString("task"),
                        rs.getLong("created"));
                item.setID(rs.getInt("id"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получение списка по имени.
     * @param name name.
     * @return items.
     */
    ArrayList<Item> findByName(String name) {
        ArrayList<Item> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
             PreparedStatement st = conn.prepareStatement(bd.sqlFindByName)) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item item = new Item(
                        rs.getString("name"),
                        rs.getString("task"),
                        rs.getLong("created"));
                item.setID(rs.getInt("id"));
                result.add(item);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получение заявки по id.
     * @param id id.
     * @return item.
     */
    Item findById(Integer id) {
        Item result = null;
        try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
             PreparedStatement st = conn.prepareStatement(bd.sqlFindByID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result = new Item(
                        rs.getString("name"),
                        rs.getString("task"),
                        rs.getLong("created"));
                result.setID(rs.getInt("id"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    void updateTask(Item item, String newTask) {
        if (item.getId() != null) {
            try (Connection conn = DriverManager.getConnection(bd.url, bd.username, bd.password);
                 PreparedStatement st = conn.prepareStatement(bd.sqlUpdateTask)) {
                st.setString(1, newTask);
                st.setInt(2, item.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @return string.
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Item> items = findAll();
        for (Item item: items) {
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }
}

@XmlRootElement(name = "tracker")
class BDTracker {
    String url;
    String username;
    String password;
    String sqlCreateTable;
    String sqlAddItem;
    String sqlRemoveItem;
    String sqlFindAll;
    String sqlFindByName;
    String sqlFindByID;
    String sqlUpdateTask;
    String sqlClearBase;

    public String getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSqlCreateTable() {
        return sqlCreateTable;
    }

    @XmlElement
    public void setSqlCreateTable(String sqlCreateTable) {
        this.sqlCreateTable = sqlCreateTable;
    }

    public String getSqlAddItem() {
        return sqlAddItem;
    }

    @XmlElement
    public void setSqlAddItem(String sqlAddItem) {
        this.sqlAddItem = sqlAddItem;
    }

    public String getSqlRemoveItem() {
        return sqlRemoveItem;
    }

    @XmlElement
    public void setSqlRemoveItem(String sqlRemoveItem) {
        this.sqlRemoveItem = sqlRemoveItem;
    }

    public String getSqlFindAll() {
        return sqlFindAll;
    }

    @XmlElement
    public void setSqlFindAll(String sqlFindAll) {
        this.sqlFindAll = sqlFindAll;
    }

    public String getSqlFindByName() {
        return sqlFindByName;
    }

    @XmlElement
    public void setSqlFindByName(String sqlFindByName) {
        this.sqlFindByName = sqlFindByName;
    }

    public String getSqlFindByID() {
        return sqlFindByID;
    }

    @XmlElement
    public void setSqlFindByID(String sqlFindByID) {
        this.sqlFindByID = sqlFindByID;
    }

    public String getSqlUpdateTask() {
        return sqlUpdateTask;
    }

    @XmlElement
    public void setSqlUpdateTask(String sqlUpdateTask) {
        this.sqlUpdateTask = sqlUpdateTask;
    }

    public String getSqlClearBase() {
        return sqlClearBase;
    }

    @XmlElement
    public void setSqlClearBase(String sqlClearBase) {
        this.sqlClearBase = sqlClearBase;
    }
}
