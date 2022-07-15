package com.example.koreanrestaurantji.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //데이터 값을 가저오는 get 메소드 자동 생성
@Setter //데이터 값을 설정하는 set 메소드 자동 생성
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
public class UserLoginRequestDto {  //RequestDto는 입력값을 받을 때 사용함.
                                    // 이 경우 로그인 input 데이터인 id와 pwd를 명시
    private String userEmail;
    private String userPassword;

    @Builder
    public UserLoginRequestDto(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}