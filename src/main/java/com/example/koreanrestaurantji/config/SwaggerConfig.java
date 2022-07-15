package com.example.koreanrestaurantji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration //Bean을 등록함을 의미. (설정파일임을 의미)
public class SwaggerConfig {

    // Docket : Swagger 설정의 핵심. 문서화 객체, Controller에서 API에 대한 내용 및 스펙 설정 가능.
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo()).select()  //제목, 설명 등 문서 정보를 위해 호출
                .apis(RequestHandlerSelectors.any()) //api 스펙이 작성되어 있는 패키지를 지정. @RequestMapping이 선언된 API를 문서화.
                .paths(PathSelectors.any()) // path 조건에 해당하는 API를 찾아 문서화
                .build();
    }

    // ApiInfo : Swagger 제목, 설명 등 문서 정보를 위해 호출.
    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Korean Restaurant JI's SpringBoot REST API ")
                .version("1.0.0")
                .description("'한식 전문점 JI' 웹사이트의 swagger api 입니다.")
                .build();
    }
}
