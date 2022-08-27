package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.question.*;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.QuestionRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public SuccessResponseDto createQNA(QNACreateRequestDto QNACreateRequestDto) {
        User user = findUserByToken();
        QuestionCreateDto questionCreateDto = new QuestionCreateDto(user, QNACreateRequestDto.getQuestionTitle(),
                QNACreateRequestDto.getQuestionContents(), QNACreateRequestDto.getIsPrivate(), true);

        try {
            questionRepository.save(questionCreateDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_QNA);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto createFNQ(FNQCreateRequestDto fnqCreateRequestDto) {
        User user = findUserByToken();
        if(user.isRole()) {
            QuestionCreateDto questionCreateDto = new QuestionCreateDto(user, fnqCreateRequestDto.getQuestionTitle(),
                    fnqCreateRequestDto.getQuestionContents(), false, false);

            try {
                questionRepository.save(questionCreateDto.toEntity());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_FNQ);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<QNAResponseDto> findQNAList() {
        return questionRepository.findByIsQNAIsTrueOrderByWriteDateDesc()
                .stream()
                .map(QNAResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<FNQResponseDto> findFNQList() {
        return questionRepository.findByIsQNAIsFalseOrderByWriteDateAsc()
                .stream()
                .map(FNQResponseDto::new)
                .collect(Collectors.toList());
    }

    public QuestionBoard findQuestionBoardByQuestionNumber(long questionNumber){
        return questionRepository.findByQuestionNumber(questionNumber).orElseThrow(() -> new BaseException(BaseResponseCode.QNA_NOT_FOUND));
    }

    public QuestionPostResponseDto findQNAPublicPost(long questionNumber){
        return new QuestionPostResponseDto(findQuestionBoardByQuestionNumber(questionNumber));
    }

    public QuestionPostResponseDto findQNAPrivatePost(long questionNumber) {
        User user = findUserByToken();
        QuestionBoard questionBoard = findQuestionBoardByQuestionNumber(questionNumber);

        if (user.equals(questionBoard.getUser()) || user.isRole()) {
            return new QuestionPostResponseDto(questionBoard);
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
    }

    public QuestionPostResponseDto findFNQPost(long questionNumber){
        return new QuestionPostResponseDto(findQuestionBoardByQuestionNumber(questionNumber));
    }

    public SuccessResponseDto updateQNAPost(long questionNumber, QNAUpdateRequestDto qnaUpdateRequestDto){
        User user = findUserByToken();

        if(user == findQuestionBoardByQuestionNumber(questionNumber).getUser() || user.isRole()) {
            try {
                questionRepository.updateQNAPost(questionNumber, qnaUpdateRequestDto.getQuestionTitle(), qnaUpdateRequestDto.getQuestionContents(), qnaUpdateRequestDto.getIsPrivate());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_QNA);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto updateFNQPost(long questionNumber, FNQUpdateRequestDto fnqUpdateRequestDto){
        User user = findUserByToken();

        if(user.isRole()) {
            try {
                questionRepository.updateFNQPost(questionNumber, fnqUpdateRequestDto.getQuestionTitle(), fnqUpdateRequestDto.getQuestionContents());
            } catch (Exception e) {
                System.out.println(e);
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_FNQ);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(Long questionNumber){
        User user = findUserByToken();
        QuestionBoard questionBoard = findQuestionBoardByQuestionNumber(questionNumber);

        if(user == questionBoard.getUser() || user.isRole()) {
            try {
                questionRepository.delete(questionBoard);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
