package ru.job4j.carpricespr.service;

import org.springframework.security.core.context.SecurityContext;
import ru.job4j.carpricespr.items.User;

public interface UserService extends EntityService<User> {

    User findByLogin(String login);

    User getLoginUserBySecurityContext(SecurityContext context);

}
