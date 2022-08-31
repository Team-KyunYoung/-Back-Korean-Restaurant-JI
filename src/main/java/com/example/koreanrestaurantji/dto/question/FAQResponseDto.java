package com.example.koreanrestaurantji.dto.question;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FAQResponseDto {
    private final Long questionNumber;
    private final String questionTitle;
    private final String questionContents;

    public FAQResponseDto(QuestionBoard questionBoard){
        this.questionNumber = questionBoard.getQuestionNumber();
        this.questionTitle = questionBoard.getQuestionTitle();
        this.questionContents = questionBoard.getQuestionContents();
    }
}
