package com.example.koreanrestaurantji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:application-auth.properties" })
public class KoreanRestaurantJiBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoreanRestaurantJiBackApplication.class, args);
    }

}
