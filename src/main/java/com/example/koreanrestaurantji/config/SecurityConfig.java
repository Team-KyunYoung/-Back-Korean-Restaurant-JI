package com.example.koreanrestaurantji.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션(Autowired 안써도됨)
@Configuration //Bean을 등록함을 의미. (설정파일임을 의미)
public class SecurityConfig{

    // Spring Security PWD 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder : BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 인코딩해주는 메서드와
        // 사용자의 의해 제출된 비밀번호와 저장소에 저장되어 있는 비밀번호의 일치 여부를 확인해주는 메서드를 제공
        return new BCryptPasswordEncoder();
    }

    //Spring Security 인증 확인 (SecurityFilterChain, WebSecurityCustomizer)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 사용권한 체크(요청에 대한 권한을 지정)
                // 인증 필요 없는 기능에 접근 할 수 있도록 설정(Controller에 정의된 url 넣으면 된다)
                .antMatchers("/exception/**", "/item/**", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // For Swagger
                .antMatchers("/user/signup/**", "/user/login/**", "/user/logout/**").permitAll() // 로그인, 회원가입은 누구나 접근 가능
                .anyRequest().authenticated() //이 외에는 인증이 필요하도록 한다.
                .and()
                .csrf().disable(); // csrf에 대해 체크하기 때문에 POST가 정상적으로 수행되지 않으므로 해지 필요.
        return http.build();
    }
}
