package com.example.koreanrestaurantji;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//SpringBootServletInitializer를 상속 받는 다는 것은 tomcat 같은
//Servlet Container 환경에서Spring Boot 애플리케이션 동작 가능 하도록
//웹 애플리케이션 컨텍스트를 구성한다는 의미
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(KoreanRestaurantJiBackApplication.class);
    }

}
