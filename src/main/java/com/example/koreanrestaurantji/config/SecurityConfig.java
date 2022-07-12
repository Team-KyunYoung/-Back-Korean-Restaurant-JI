package com.example.koreanrestaurantji.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 암호화에 필요한 PasswordEncoder 를 Bean.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override // Authorization
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/exception/**", "/item/**", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // For Swagger
                .antMatchers("/api/v1/register/**", "/api/v1/signup/**", "/api/v1/login/**", "/api/v1/logout/**").permitAll(); // 로그인, 회원가입은 누구나 접근 가능
    }
}
