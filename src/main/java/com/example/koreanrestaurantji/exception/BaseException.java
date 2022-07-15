package com.example.koreanrestaurantji.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //@Autowired이 붙은 필드의 생성자를 자동 생성
// RuntimeException : 실행 중에 발생하며 시스템 환경적으로나, INPUT 값이 잘못된 경우, 발생됨.
public class BaseException extends RuntimeException {
    //BaseResponseCode 에 정의된 상태값과 안내문이 리턴된다.
    public final BaseResponseCode baseResponseCode;
}