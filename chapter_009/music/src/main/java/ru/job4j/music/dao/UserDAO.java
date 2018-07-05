package ru.job4j.music.dao;

import ru.job4j.music.entities.MusicType;
import ru.job4j.music.entities.User;
import ru.job4j.music.entities.Role;
import ru.job4j.music.entities.Address;

import java.util.Collection;

public interface UserDAO extends DAO<User> {
    @Override
    User find(String login) throws StoreException;
    Collection<MusicType> findMusic(int userId) throws StoreException;
    Collection<User> find(MusicType musicType) throws StoreException;
    Collection<User> find(Role role) throws StoreException;
    User find(Address address) throws StoreException;
    void addMusicPref(int userId, int musicId) throws StoreException;
    void delMusicPref(int userId, int musicId) throws StoreException;
}
