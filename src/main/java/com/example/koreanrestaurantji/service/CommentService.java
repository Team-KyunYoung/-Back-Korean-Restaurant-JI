package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Comment;
import com.example.koreanrestaurantji.domain.QuestionBoard;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.comment.CommentResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public Boolean findUserByTokenIsRole(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND)).isRole();
    }

    public QuestionBoard findQNAByquestionNumber(long questionNumber) {
        return questionRepository.findByQuestionNumber(questionNumber).orElseThrow(() -> new BaseException(BaseResponseCode.QNA_NOT_FOUND));
    }

    public SuccessResponseDto create(long questionNumber, String comment) {
        if(findUserByTokenIsRole()) {
            try {
                commentRepository.save(new Comment(findQNAByquestionNumber(questionNumber), comment));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_COMMENT);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public Boolean existsComment(long questionNumber) {
        return commentRepository.existsByQuestionBoard(findQNAByquestionNumber(questionNumber));
    }

    public List<CommentResponseDto> findCommentList(long questionNumber) {
        return commentRepository.findByQuestionBoardOrderByWriteDateDesc(findQNAByquestionNumber(questionNumber))
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public SuccessResponseDto updateComment(long commentNumber, String comment){
        if(findUserByTokenIsRole()) {
            try {
                commentRepository.updateComment(commentNumber, comment);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_COMMENT);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(Long commentNumber){
        Comment comment = commentRepository.findByCommentNumber(commentNumber).orElseThrow(() -> new BaseException(BaseResponseCode.QNA_COMMENT_NOT_FOUND));
        if(findUserByTokenIsRole()) {
            try {
                commentRepository.delete(comment);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
