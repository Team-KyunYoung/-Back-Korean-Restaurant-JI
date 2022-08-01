package com.example.koreanrestaurantji.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크(요청에 대한 권한을 지정)
                // 인증 필요 없는 기능에 접근 할 수 있도록 설정(Controller에 정의된 url 넣으면 된다)
                .antMatchers("/exception/**", "/item/**", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // For Swagger
                .antMatchers("/api/user/signup/**", "/api/user/login/**", "/api/user/logout/**", "/api/user/checknickname", "/api/user/signup/emailAuth").permitAll() // 로그인, 회원가입은 누구나 접근 가능
                .anyRequest().authenticated(); //이 외에는 인증이 필요하도록 한다.
        return http.build();
    }
}
