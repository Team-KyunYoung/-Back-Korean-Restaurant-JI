package com.example.koreanrestaurantji.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 생성
public class UserLoginResponseDto { //ResponseDto 출력 값을 보낼 때 사용함.
    private HttpStatus status;
    private String token;
}
