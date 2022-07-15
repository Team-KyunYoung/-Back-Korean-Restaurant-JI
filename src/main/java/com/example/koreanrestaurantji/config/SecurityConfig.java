package com.example.koreanrestaurantji.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // 암호화에 필요한 PasswordEncoder 를 Bean.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/exception/**", "/item/**", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // For Swagger
                .antMatchers("/user/signup/**", "/user/login/**", "/user/logout/**").permitAll() // 로그인, 회원가입은 누구나 접근 가능
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        return http.build();
    }

//    @Override // Authorization
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .authorizeRequests() // 요청에 대한 사용권한 체크
//                .antMatchers("/exception/**", "/item/**", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // For Swagger
//                .antMatchers("/user/signup/**", "/user/login/**", "/user/logout/**").permitAll(); // 로그인, 회원가입은 누구나 접근 가능
//    }

}
