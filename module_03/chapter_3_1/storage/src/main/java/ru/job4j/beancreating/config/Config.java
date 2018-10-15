package ru.job4j.beancreating.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.job4j.beancreating.xml.MemoryStorage;

@Configuration
public class Config {

    @Bean
    public MemoryStorage memory() {
        return new MemoryStorage();
    }

    @Bean
    public UserStorageConfig memStorage() {
        return new UserStorageConfig(memory());
    }

}
