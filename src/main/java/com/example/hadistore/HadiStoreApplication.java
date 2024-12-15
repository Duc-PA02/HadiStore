package com.example.hadistore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HadiStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(HadiStoreApplication.class, args);
    }

}
