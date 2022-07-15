package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.dto.user.UserLoginRequestDto;
import com.example.koreanrestaurantji.dto.user.UserLoginResponseDto;
import com.example.koreanrestaurantji.dto.user.UserSignupRequestDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(UserSignupRequestDto userSignupRequestDto) throws BaseException {
        System.out.println(userSignupRequestDto.getUserPassword());
        userSignupRequestDto.setUserPassword(passwordEncoder.encode(userSignupRequestDto.getUserPassword()));
        boolean exitsUserCheck = userRepository.existsByUserEmail(userSignupRequestDto.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.BAD_REQUEST));
        System.out.println(userSignupRequestDto.getUserPassword());

        if (exitsUserCheck) {
            throw new BaseException(BaseResponseCode.DUPLICATE_EMAIL);
        }
        Long id;
        try {
            id = userRepository.save(userSignupRequestDto.toEntity()).getUserId();
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_USER);
        }
        return id;
    }

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByUserEmail(userLoginRequestDto.getUserEmail()).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getPassword()))
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);

        return new UserLoginResponseDto(HttpStatus.OK);
    }

}
