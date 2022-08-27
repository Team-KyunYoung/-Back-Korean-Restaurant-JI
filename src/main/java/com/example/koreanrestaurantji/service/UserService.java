package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.user.*;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.UserRepository;
import com.example.koreanrestaurantji.util.JwtTokenProvider;
import com.example.koreanrestaurantji.util.SendEmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service //내부에서 자바 로직을 처리
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailUtil sendEmailUtil;
    private final JwtTokenProvider jwtTokenProvider;

    //UserSignupRequestDto에 명시된 데이터 셋 : email, nickname, pwd. 즉, signUp 함수에 이 데이터 셋이 전달된다.
    public Long signUp(UserSignupRequestDto userSignupRequestDto) throws BaseException {
        // passwordEncoder.encode() : 패스워드를 암호화해주는 메서드. SHA-1, 8바이트로 결합된 해쉬, 랜덤 하게 생성된 솔트(salt)를 지원
        // 똑같은 비밀번호를 해당 메서드를 통하여 인코딩하더라도 매번 다른 인코딩 된 문자열을 반환함.
        // userSignupRequestDto.getUserPassword() : 전달된 userSignupRequestDto 데이터에서 getUserPassword를 통해 pwd 값만 가져온다.
        // userSignupRequestDto.setUserPassword() : 전달된 userSignupRequestDto 데이터에서 ()안의 값으로 pwd를 재설정한다.
        userSignupRequestDto.setUserPassword(passwordEncoder.encode(userSignupRequestDto.getUserPassword()));

        Long userNumber;
        try {
            //userRepository.save() : jpa Repository에는 ()안의 값을 db에 insert하는 save함수가 내장되어있다.
            //userSignupRequestDto.toEntity() : userSignupRequestDto에 @Builder로 명시된 데이터가 리턴된다.
            //getUserNumber() : save()된 후 userRepository에 저장된 id값을 불러온다.
            userNumber = userRepository.save(userSignupRequestDto.toEntity()).getUserNumber();
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_USER);
        }
        return userNumber;
    }

    public SuccessResponseDto nicknameCheck(String nickname) {
        boolean exitsUserCheck = userRepository.existsByUserNickname(nickname).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));

        if (exitsUserCheck) {
            //존재하면 fail. 이미 있는 닉네임인것.
            throw new BaseException(BaseResponseCode.DUPLICATE_NICKNAME);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public String signupEmailAuth(String userEmail) {
        // userRepository.existsByUserEmail() : userRepository에 정의된 메서드. boolean값이 리턴됨.
        boolean exitsUserCheck = userRepository.existsByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.BAD_REQUEST));

        if (exitsUserCheck) { // existsByUserEmail() 결과로, 이미 존재하는 email이면 true
            throw new BaseException(BaseResponseCode.DUPLICATE_EMAIL);
        }

        //없는 email이면, 이메일 인증
        String authCode = "";
        try {
            authCode = sendEmailUtil.sendSimpleMessage(userEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authCode;
    }

    // return 될 데이터 셋이 UserLoginResponseDto에 명시되어 있으므로, 함수 유형이 UserLoginResponseDto.
    //UserLoginRequestDto 명시된 데이터 셋 : email, pwd. 즉, login 함수에 이 데이터 셋이 전달된다.
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
                                                                                     //orElseThrow() : userRepository.findByUserEmail()의 값이 없으면 ()안의 함수 실행
        User user = userRepository.findByUserEmail(userLoginRequestDto.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        // passwordEncoder.matches() : 제출된 인코딩 되지 않은 패스워드(로그인)와 인코딩 된 패스워드(db)의 일치 여부를 확인. 반환 타입은 boolean.
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getUserPassword())) //User.java에서 @Getter의 사용으로 getUserPassword() 자동 생성.
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);

        String token = jwtTokenProvider.createToken(userLoginRequestDto.getUserEmail());
        return new UserLoginResponseDto(HttpStatus.OK, token);
    }

    public UserResponseDto findByUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        String userEmail = context.getAuthentication().getName();
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        return new UserResponseDto(user);
    }

    public String findEmailAuth(String userEmail) {
        boolean exitsUserCheck = userRepository.existsByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.BAD_REQUEST));

        if (!exitsUserCheck) { // 가입된 email이 아니면,
            throw new BaseException(BaseResponseCode.DUPLICATE_EMAIL);
        }

        //없는 email이면, 이메일 인증
        String authCode = "";
        try {
            authCode = sendEmailUtil.sendSimpleMessage(userEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authCode;
    }

    public SuccessResponseDto updateNickname(UserUpdateNameRequestDto userUpdateNameDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        String userEmail = context.getAuthentication().getName();
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));

        user.setUserNickname(userUpdateNameDto.getUserNickname());
        userRepository.save(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto fineUpdatePassword(UserFindUpdatePwdRequestDto userFindUpdatePwdRequestDto) {
        User user = userRepository.findByUserEmail(userFindUpdatePwdRequestDto.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));

        user.setUserPassword(passwordEncoder.encode(userFindUpdatePwdRequestDto.getUserPassword()));
        userRepository.save(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto verifyPassword(UserUpdatePwdRequestDto userUpdatePwdDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        String userEmail = context.getAuthentication().getName();
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(userUpdatePwdDto.getUserPassword(), user.getUserPassword()))
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto updatePassword(UserUpdatePwdRequestDto userUpdatePwdDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        String userEmail = context.getAuthentication().getName();
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));

        user.setUserPassword(passwordEncoder.encode(userUpdatePwdDto.getUserPassword()));
        userRepository.save(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto deleteUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        String userEmail = context.getAuthentication().getName();
        //삭제하려는 아이디 userRepository에서 찾기
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        userRepository.delete(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }
}
