package ru.job4j.music.dao;

import ru.job4j.music.entities.MusicType;

import java.sql.SQLException;

public interface MusicTypeDAO extends DAO<MusicType> {
    @Override
    MusicType find(String name) throws StoreException;
}
