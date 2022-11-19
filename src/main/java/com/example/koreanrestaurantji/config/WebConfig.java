package com.example.koreanrestaurantji.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                                //AWS CloudFront 주소                     //대체 도메인
                .allowedOrigins("https://d33eeenzlmjw0a.cloudfront.net", "https://www.koreanrestaurantji.ml")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
