package ru.job4j.music.dao;

import ru.job4j.music.entities.Address;

public interface AddressDAO extends DAO<Address> {
    @Override
    Address find(String address) throws StoreException;
}
