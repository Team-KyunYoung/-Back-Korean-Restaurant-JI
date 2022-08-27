package com.example.koreanrestaurantji.dto.question;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import com.example.koreanrestaurantji.domain.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class QuestionCreateDto {
    private User user;
    private String questionTitle;
    private String questionContents;
    private boolean isQNA;
    private boolean isPrivate;

    @Builder
    public QuestionCreateDto(User user, String title, String contents, Boolean isPrivate, Boolean isQNA) {
        this.user = user;
        this.questionTitle = title;
        this.questionContents = contents;
        this.isPrivate = isPrivate;
        this.isQNA = isQNA;
    }

    public QuestionBoard toEntity() {
        return QuestionBoard.builder()
                .user(user)
                .questionTitle(questionTitle)
                .questionContents(questionContents)
                .isQNA(isQNA)
                .isPrivate(isPrivate)
                .build();
    }
}
