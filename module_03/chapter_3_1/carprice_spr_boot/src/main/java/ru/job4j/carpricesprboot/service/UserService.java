package ru.job4j.carpricesprboot.service;

import org.springframework.security.core.context.SecurityContext;
import ru.job4j.carpricesprboot.domain.User;

public interface UserService extends EntityService<User> {

    User findByLogin(String login);

    User getLoginUserBySecurityContext(SecurityContext context);

}
