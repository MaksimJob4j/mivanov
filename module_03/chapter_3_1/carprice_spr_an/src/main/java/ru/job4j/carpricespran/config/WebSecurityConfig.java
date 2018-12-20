package ru.job4j.carpricespran.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.carpricespran.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    /*
    // Вариант 1
    @Autowired
    private DataSource dataSource;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select login, password, true from users where login=?"
                )
                .authoritiesByUsernameQuery(
                        "SELECT u.login, r.name from roles as r, users as u "
                        + "where r.id = u.role_id and u.login=?"
                )
                .passwordEncoder(bCryptEncoder);
    }
*/

    // Вариант 2
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
                    .antMatchers("/", "/carFilter").permitAll()
//                    .antMatchers("/createUser").hasRole("ROLE_ADMIN")
//                     алтернативный вариант - в коде @PreAuthorize("hasAuthority('ROLE_ADMIN')")
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/")
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