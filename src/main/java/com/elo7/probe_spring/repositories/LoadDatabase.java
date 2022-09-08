package com.elo7.probe_spring.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PlateauRepository repository) {
        return args -> {
            log.info("--------------------- TEST ---------------------");
            System.out.println(repository.toString());
        };
    }
}
