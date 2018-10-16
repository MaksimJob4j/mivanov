package ru.job4j.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ImportUser {
    private final static Logger LOGGER = LogManager.getLogger(ImportUser.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-storage.xml");
        UserStorage storage = (UserStorage) context.getBean("userStorageJdbc");
        User user = new User();
        user.setLogin("Max");
        storage.create(user);
        System.out.println(user);

    }
}
