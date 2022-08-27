package com.example.koreanrestaurantji.dto.question;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class QNAResponseDto {
    private final Long questionNumber;
    private final String questionTitle;
    private final String writeDate;
    private final String writer;
    private boolean isPrivate;

    public QNAResponseDto(QuestionBoard questionBoard){
        this.questionNumber = questionBoard.getQuestionNumber();
        this.questionTitle = questionBoard.getQuestionTitle();
        this.writeDate = questionBoard.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.writer = questionBoard.getUser().getUserNickname();
        this.isPrivate = questionBoard.isPrivate();
    }
}
