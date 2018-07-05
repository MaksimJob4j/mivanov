package ru.job4j.music.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.dao.UserDAO;
import ru.job4j.music.entities.Address;
import ru.job4j.music.entities.MusicType;
import ru.job4j.music.entities.Role;
import ru.job4j.music.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStoreUser implements UserDAO {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.store.DBStoreUser.class);

    private static final DBStoreUser INSTANCE = new DBStoreUser();

    private DBStoreUser() {
    }

    public static DBStoreUser getInstance() {
        return INSTANCE;
    }

    private BasicDataSource getDataSource() throws StoreException {
        LOGGER.traceEntry();
        return DBStore.getInstance().getDataSource();
    }

    @Override
    public void create(User user) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement psAddress = connection.prepareStatement(
                     "INSERT INTO addresses (country, city, rest) "
                             + "VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement psUserAdr = connection.prepareStatement(
                     "INSERT INTO users (login, password, id_role, id_address) "
                             + "VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement psUser = connection.prepareStatement(
                     "INSERT INTO users (login, password, id_role) "
                             + "VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            if (user.getAddress() != null) {
                psAddress.setString(1, user.getAddress().getCountry());
                psAddress.setString(2, user.getAddress().getCity());
                psAddress.setString(3, user.getAddress().getRestAddress());
                psAddress.executeUpdate();
                ResultSet gkAddress = psAddress.getGeneratedKeys();
                if (gkAddress.next()) {
                    user.getAddress().setId(gkAddress.getInt("id"));
                }
                psUserAdr.setString(1, user.getLogin());
                psUserAdr.setString(2, user.getPassword());
                psUserAdr.setInt(3, user.getRole().getId());
                psUserAdr.setInt(4, user.getAddress().getId());
                psUserAdr.executeUpdate();
                ResultSet gkUser = psUserAdr.getGeneratedKeys();
                if (gkUser.next()) {
                    user.setId(gkUser.getInt("id"));
                }
            } else {
                psUser.setString(1, user.getLogin());
                psUser.setString(2, user.getPassword());
                psUser.setInt(3, user.getRole().getId());
                psUser.executeUpdate();
                ResultSet gkUser = psUser.getGeneratedKeys();
                if (gkUser.next()) {
                    user.setId(gkUser.getInt("id"));
                }
            }
            connection.commit();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User save store error. " + e.getMessage());
        }
    }

    @Override
    public void update(User user) throws StoreException {
        LOGGER.traceEntry();
        if (user.getAddress() == null) {
            try (Connection connection = getDataSource().getConnection();
                 Statement st = connection.createStatement();
                 ResultSet rsIdAddress = st.executeQuery(
                         "SELECT id_address FROM users WHERE id = \'" + user.getId() + "\';");
                 PreparedStatement psUserUpdate = connection.prepareStatement(
                         "UPDATE users SET login = ?, password = ?, id_role = ?, id_address = ? WHERE id = ?");
                 PreparedStatement psAddressDelete = connection.prepareStatement("DELETE FROM addresses WHERE id = ?;")
            ) {
                connection.setAutoCommit(false);
                Integer idAddress = null;
                if (rsIdAddress.next()) {
                    idAddress = rsIdAddress.getInt("id_address");
                }
                psUserUpdate.setString(1, user.getLogin());
                psUserUpdate.setString(2, user.getPassword());
                psUserUpdate.setInt(3, user.getRole().getId());
                psUserUpdate.setNull(4, Types.INTEGER);
                psUserUpdate.setInt(5, user.getId());
                psUserUpdate.executeUpdate();
                if (idAddress != null) {
                    psAddressDelete.setInt(1, idAddress);
                    psAddressDelete.executeUpdate();
                }
                connection.commit();
            } catch (java.sql.SQLException e) {
                LOGGER.error("error", e);
                throw new StoreException("User update error. " + e.getMessage());
            }
        } else {
            try (Connection connection = getDataSource().getConnection();
                 Statement st = connection.createStatement();
                 ResultSet rsIdAddress = st.executeQuery(
                         "SELECT id_address FROM users WHERE id = \'" + user.getId() + "\';");
                 PreparedStatement psNewAddress = connection.prepareStatement(
                         "INSERT INTO addresses (country, city, rest) VALUES (?, ?, ?)",
                         PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement psAddressUpdate = connection.prepareStatement(
                         "UPDATE addresses SET country=?, city=?, rest=? WHERE id = ?");
                 PreparedStatement psUserUpdate = connection.prepareStatement(
                         "UPDATE users SET login = ?, password = ?, id_role = ?, id_address = ? WHERE id = ?")) {
                connection.setAutoCommit(false);
                Integer idAddress = null;
                if (rsIdAddress.next()) {
                    idAddress = rsIdAddress.getInt("id_address");
                }
                if (idAddress == null || idAddress == 0) {
                    psNewAddress.setString(1, user.getAddress().getCountry());
                    psNewAddress.setString(2, user.getAddress().getCity());
                    psNewAddress.setString(3, user.getAddress().getRestAddress());
                    psNewAddress.executeUpdate();
                    ResultSet gk = psNewAddress.getGeneratedKeys();
                    if (gk.next()) {
                        idAddress = gk.getInt("id");
                    }
                } else {
                    psAddressUpdate.setString(1, user.getAddress().getCountry());
                    psAddressUpdate.setString(2, user.getAddress().getCity());
                    psAddressUpdate.setString(3, user.getAddress().getRestAddress());
                    psAddressUpdate.setInt(4, idAddress);
                    psAddressUpdate.executeUpdate();
                }
                psUserUpdate.setString(1, user.getLogin());
                psUserUpdate.setString(2, user.getPassword());
                psUserUpdate.setInt(3, user.getRole().getId());
                psUserUpdate.setInt(4, idAddress);
                psUserUpdate.setInt(5, user.getId());
                psUserUpdate.executeUpdate();
                connection.commit();
            } catch (java.sql.SQLException e) {
                LOGGER.error("error", e);
                throw new StoreException("User update error. " + e.getMessage());
            }
        }
    }

    @Override
    public void delete(int id) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement psMusicPref = connection.prepareStatement("DELETE FROM music_pref WHERE id_user = ?;");
             Statement st = connection.createStatement();
             ResultSet rsIdAddress = st.executeQuery("SELECT id_address FROM users WHERE id = \'" + id + "\';");
             PreparedStatement psUsersDelete = connection.prepareStatement("DELETE FROM users WHERE id = ?;");
             PreparedStatement psAddressDelete = connection.prepareStatement("DELETE FROM addresses WHERE id = ?;")
        ) {
            connection.setAutoCommit(false);
            psMusicPref.setInt(1, id);
            psMusicPref.executeUpdate();
            Integer idAddress = null;
            if (rsIdAddress.next()) {
                idAddress = rsIdAddress.getInt("id_address");
            }
            psUsersDelete.setInt(1, id);
            psUsersDelete.executeUpdate();
            if (idAddress != null) {
                psAddressDelete.setInt(1, idAddress);
                psAddressDelete.executeUpdate();
            }
            connection.commit();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User delete error. " + e.getMessage());
        }
    }

    @Override
    public List<User> find() throws StoreException {
        LOGGER.traceEntry();
        List<User> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                result.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                DBStoreRole.getInstance().find(rs.getInt("id_role")),
                                DBStoreAddress.getInstance().find(rs.getInt("id_address")),
                                findMusic(rs.getInt("id"))
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User read error." + e.getMessage());
        }
        return result;
    }

    @Override
    public User find(int id) throws StoreException {
        LOGGER.traceEntry();
        return findFirstUser("id", String.valueOf(id));
    }

    @Override
    public User find(String login) throws StoreException {
        LOGGER.traceEntry();
        return findFirstUser("login", login);
    }

    private User findFirstUser(String fieldName, String fieldVolume) throws StoreException {
        LOGGER.traceEntry();
        User result = null;
        String query = "SELECT * FROM users WHERE " + fieldName + " = '" + fieldVolume + "'";
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                result = new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        DBStoreRole.getInstance().find(rs.getInt("id_role")),
                        DBStoreAddress.getInstance().find(rs.getInt("id_address")),
                        findMusic(rs.getInt("id"))
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User read error. " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<MusicType> findMusic(int userId) throws StoreException {
        LOGGER.traceEntry();
        List<MusicType> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT * FROM music WHERE id IN ( SELECT id_music FROM music_pref WHERE id_user = '" + userId + "')")) {
            while (rs.next()) {
                result.add(
                        new MusicType(
                                rs.getInt("id"),
                                rs.getString("name")
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("Music read error." + e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> find(MusicType musicType) throws StoreException {
        LOGGER.traceEntry();
        List<User> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT * FROM users WHERE id IN ( SELECT id_user FROM music_pref WHERE id_music = '" + musicType.getId() + "')")) {
            while (rs.next()) {
                result.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                DBStoreRole.getInstance().find(rs.getInt("id_role")),
                                DBStoreAddress.getInstance().find(rs.getInt("id_address")),
                                DBStoreUser.getInstance().findMusic(rs.getInt("id"))
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User read error." + e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> find(Role role) throws StoreException {
        LOGGER.traceEntry();
        List<User> result = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT * FROM users WHERE id_role = '" + role.getId() + "'")) {
            while (rs.next()) {
                result.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                DBStoreRole.getInstance().find(rs.getInt("id_role")),
                                DBStoreAddress.getInstance().find(rs.getInt("id_address")),
                                DBStoreUser.getInstance().findMusic(rs.getInt("id"))
                        )
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User read error." + e.getMessage());
        }
        return result;
    }

    @Override
    public User find(Address address) throws StoreException {
        LOGGER.traceEntry();
        User result = null;
        try (Connection connection = getDataSource().getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT * FROM users WHERE id_address = '" + address.getId() + "'")) {
            if (rs.next()) {
                result = new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        DBStoreRole.getInstance().find(rs.getInt("id_role")),
                        DBStoreAddress.getInstance().find(rs.getInt("id_address")),
                        DBStoreUser.getInstance().findMusic(rs.getInt("id"))
                );
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User read error." + e.getMessage());
        }
        return result;
    }

    @Override
    public void addMusicPref(int userId, int musicId) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO music_pref (id_user, id_music) VALUES (?, ?)");) {
            ps.setInt(1, userId);
            ps.setInt(2, musicId);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User music preference add error. " + e.getMessage());
        }
    }

    @Override
    public void delMusicPref(int userId, int musicId) throws StoreException {
        LOGGER.traceEntry();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement psDelMusPref = connection.prepareStatement(
                     "DELETE FROM music_pref WHERE id_user = ? AND  id_music = ?");
             Statement st = connection.createStatement();
             ResultSet rsUsers = st.executeQuery(
                     "SELECT * FROM music_pref WHERE id_music = '" + musicId + "'");
             PreparedStatement psDelMusType = connection.prepareStatement(
                     "DELETE FROM music WHERE id = ?");) {
            connection.setAutoCommit(false);
            psDelMusPref.setInt(1, userId);
            psDelMusPref.setInt(2, musicId);
            psDelMusPref.executeUpdate();
            rsUsers.next();
            if (!rsUsers.next()) {
                psDelMusType.setInt(1, musicId);
                psDelMusType.executeUpdate();
            }
            connection.commit();
        } catch (java.sql.SQLException e) {
            LOGGER.error("error", e);
            throw new StoreException("User music preference delete error. " + e.getMessage());
        }
    }
}