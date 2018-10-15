package ru.job4j.beancreating;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.beancreating.auto.UserStorageAuto;
import ru.job4j.beancreating.config.Config;
import ru.job4j.beancreating.config.UserStorageConfig;
import ru.job4j.beancreating.xml.UserStorageAnnotation;
import ru.job4j.beancreating.xml.UserStorageConstructor;
import ru.job4j.beancreating.xml.UserStorageProperty;

public class MainCreating {

    public static void main(String[] args) {
        User user = new User(1, "username");
        // XML constructor
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        UserStorageConstructor constructorMemStorage
                = (UserStorageConstructor) context.getBean("userStorageMemConstructor");
        constructorMemStorage.add(user);
        UserStorageConstructor constructorJdbcStorage
                = (UserStorageConstructor) context.getBean("userStorageJdbcConstructor");
        constructorJdbcStorage.add(user);
        // XML property
        UserStorageProperty propertyMemStorage
                = (UserStorageProperty) context.getBean("userStorageMemProperty");
        propertyMemStorage.add(user);
        UserStorageProperty propertyJdbcStorage
                = (UserStorageProperty) context.getBean("userStorageJdbcProperty");
        propertyJdbcStorage.add(user);
        UserStorageAnnotation annotationStorage
                = (UserStorageAnnotation) context.getBean("userStorageAnnotation");
        annotationStorage.add(user);

        // Auto scan
        ApplicationContext autoContext = new ClassPathXmlApplicationContext("spring-context-auto.xml");
        UserStorageAuto userStorageAuto = autoContext.getBean(UserStorageAuto.class);
        userStorageAuto.add(user);

        // Configuration class
        ApplicationContext cofigContext = new AnnotationConfigApplicationContext(Config.class);
        UserStorageConfig userStorageConfig = cofigContext.getBean(UserStorageConfig.class);
        userStorageConfig.add(user);
    }
}