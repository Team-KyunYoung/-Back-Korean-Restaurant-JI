package com.example.koreanrestaurantji.dto.question;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class FNQResponseDto {
    private final Long questionNumber;
    private final String questionTitle;
    private final String questionContents;

    public FNQResponseDto(QuestionBoard questionBoard){
        this.questionNumber = questionBoard.getQuestionNumber();
        this.questionTitle = questionBoard.getQuestionTitle();
        this.questionContents = questionBoard.getQuestionContents();
    }
}
