package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.User;
import ru.job4j.carpricesprboot.repository.UserRepository;
import ru.job4j.carpricesprboot.web.security.UserPrincipal;
import ru.job4j.carpricesprboot.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<User> find() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findOrdered() {
        return null;
    }

    @Override
    public Optional<User> find(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByLogin(String login) {
        LOGGER.traceEntry();
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User getLoginUserBySecurityContext(SecurityContext context) {
        User loginUser;
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            loginUser = ((UserPrincipal) principal).getUser();
        } else {
            String login;
            if (principal instanceof UserDetails) {
                login = ((UserDetails) principal).getUsername();
            } else {
                login = principal.toString();
            }
            loginUser = userRepository.findUserByLogin(login);
        }
        return loginUser;
    }
}
