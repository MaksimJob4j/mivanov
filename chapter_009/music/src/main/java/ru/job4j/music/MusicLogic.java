package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.*;
import ru.job4j.music.entities.Address;
import ru.job4j.music.entities.MusicType;
import ru.job4j.music.entities.Role;
import ru.job4j.music.entities.User;
import ru.job4j.music.store.DBStoreAddress;
import ru.job4j.music.store.DBStoreMusicType;
import ru.job4j.music.store.DBStoreRole;
import ru.job4j.music.store.DBStoreUser;

import java.util.Collection;
import java.util.List;

public class MusicLogic {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.MusicLogic.class);

    private final AddressDAO addressDAO = DBStoreAddress.getInstance();
    private final MusicTypeDAO musicTypeDAO = DBStoreMusicType.getInstance();
    private final RoleDAO roleDAO = DBStoreRole.getInstance();
    private final UserDAO userDAO = DBStoreUser.getInstance();

    private static final MusicLogic INSTANCE = new MusicLogic();

    private MusicLogic() {
    }

    public static MusicLogic getInstance() {
        LOGGER.traceEntry();
        return INSTANCE;
    }

    public User findUserByLogin(String login) throws StoreException {
        return userDAO.find(login);
    }

    public boolean isCredential(String login, String password) throws StoreException {
        LOGGER.traceEntry();
        boolean exist = false;
        User user = userDAO.find(login);
        if (user != null && password.equals(user.getPassword())) {
            exist = true;
        }
        return exist;
    }


    public List<User> findAllUsers() throws StoreException {
        return (List<User>) userDAO.find();
    }

    public List<Role> findAllRoles() throws StoreException {
        return (List<Role>) roleDAO.find();
    }

    public Role findRoleByName(String roleName) throws StoreException {
        return roleDAO.find(roleName);
    }

    public void add(User user) throws StoreException {
        userDAO.create(user);
    }

    public User findUserById(String id) throws StoreException {
        return userDAO.find(Integer.parseInt(id));
    }

    public void add(Address address) throws StoreException {
        addressDAO.create(address);
    }

    public void update(Address address) throws StoreException {
        addressDAO.update(address);
    }

    public void update(User user) throws StoreException {
        userDAO.update(user);
    }

    public List<MusicType> findAllTypes(User user) throws StoreException {
        return (List<MusicType>) userDAO.findMusic(user.getId());
    }

    public List<MusicType> findAllTypes() throws StoreException {
        return (List<MusicType>) musicTypeDAO.find();
    }

    public void deleteUser(String id) throws StoreException {
        userDAO.delete(Integer.parseInt(id));
    }

    public void addMusicPref(int userId, int typeId) throws StoreException {
        userDAO.addMusicPref(userId, typeId);
    }

    public void delMusicPref(int userId, int typeId) throws StoreException {
        userDAO.delMusicPref(userId, typeId);
    }

    public void newMusicType(String name) throws StoreException {
        musicTypeDAO.create(new MusicType(0, name));
    }

    public MusicType findMusicByName(String name) throws StoreException {
        return musicTypeDAO.find(name);
    }

    public Collection<User> findMusicPrefUsers(int musicTypeId) throws StoreException {
        return userDAO.find(musicTypeDAO.find(musicTypeId));
    }

    public void deleteMusicType(int typeId) throws StoreException {
        musicTypeDAO.delete(typeId);
    }
}