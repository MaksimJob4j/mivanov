package ru.job4j.carpricesprboot.web.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.job4j.carpricesprboot.domain.User;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private final static Logger LOGGER = LogManager.getLogger(UserPrincipal.class);

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LOGGER.traceEntry();
        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        LOGGER.traceEntry();
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        LOGGER.traceEntry();
        return this.user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        LOGGER.traceEntry();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        LOGGER.traceEntry();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LOGGER.traceEntry();
        return true;
    }

    @Override
    public boolean isEnabled() {
        LOGGER.traceEntry();
        return true;
    }

    public User getUser() {
        LOGGER.traceEntry();
        return this.user;
    }
}