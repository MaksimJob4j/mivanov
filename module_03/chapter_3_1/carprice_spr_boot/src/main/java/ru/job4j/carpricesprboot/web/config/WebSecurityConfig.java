package ru.job4j.carpricesprboot.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.carpricesprboot.web.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService).passwordEncoder(bCryptEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/carFilter", "/css/*", "/js/*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/signin")
                    .and()
                .formLogin()
                    .loginPage("/signin").permitAll()
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .loginProcessingUrl("/signin")
                    .defaultSuccessUrl("/")
                    .failureUrl("/signin?error=error")
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/signin?logout=true")
                    .and()
                .csrf()
                    .disable();
    }
}