package com.example.koreanrestaurantji.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 생성
@NoArgsConstructor
//set은 쓰지마쇼 외부사용자 이용 가능해짐
public class UserDeleteRequestDto {

    private String userEmail;
}
