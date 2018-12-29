package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.Role;
import ru.job4j.carpricesprboot.repository.RoleRepository;
import ru.job4j.carpricesprboot.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final static Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void create(Role user) {
        roleRepository.save(user);
    }

    @Override
    public Iterable<Role> find() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findOrdered() {
        return null;
    }

    @Override
    public Optional<Role> find(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Role findByName(String name) {
        LOGGER.traceEntry();
        return roleRepository.findRoleByName(name);
    }
}
