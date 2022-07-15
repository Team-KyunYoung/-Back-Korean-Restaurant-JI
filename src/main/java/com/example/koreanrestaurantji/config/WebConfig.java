package com.example.koreanrestaurantji.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Bean을 등록함을 의미. (설정파일임을 의미)
public class WebConfig implements WebMvcConfigurer { //WebMvcConfigurer를 통해 CORS 설정 Override 가능
    // CORS : 크로스 도메인 이슈를 해결하기 위한 코드
    //크로스 도메인 이슈: 브라우저에서 다른 도메인으로 URL 요청을 하는 경우 나타나는 보안문제
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //해당 서버의 "/api/**" 요청에 해당 Class가 동작
                .allowedOrigins("http://localhost:8080", "http://localhost:3000") // 원하는 도메인만 허용
                .allowedMethods("*") // 원하는 HTTP 메소드만 허용 (GET, POST, PUT, DELETE, OPTIONS, ...)
                .allowedHeaders("*") // 원하는 헤더만 허용(Http Request Header에 허용해줄 Header Name을 설정)
                .allowCredentials(false); // 쿠키 요청 허용 (true -> 보안상 이슈 발생 가능)
    }
}
