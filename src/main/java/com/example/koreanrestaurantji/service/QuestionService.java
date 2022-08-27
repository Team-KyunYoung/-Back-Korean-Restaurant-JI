package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.QuestionRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public User findUserByToken(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                        .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }
}
