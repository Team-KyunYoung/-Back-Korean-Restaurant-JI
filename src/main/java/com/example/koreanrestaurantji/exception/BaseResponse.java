package com.example.koreanrestaurantji.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {  //기본 응답 클래스. Controller에서 리턴될 항목을 정의한다.

    @ApiModelProperty(value = "HttpStatus Code", example = "OK")
    private HttpStatus httpStatus;  // 상태 코드 메세지

    @ApiModelProperty(value = "응답 메시지", example = "요청 성공하였습니다.")
    private String message; // 에러 설명

    @ApiModelProperty(value = "응답 result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data; //실질적으로 사용될 data
}
