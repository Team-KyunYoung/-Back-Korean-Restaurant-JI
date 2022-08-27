package com.example.koreanrestaurantji.dto.comment;

import com.example.koreanrestaurantji.domain.Comment;
import com.example.koreanrestaurantji.domain.EventBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private final Long commentNumber;
    private final String commentContents;
    private final String writeDate;

    public CommentResponseDto(Comment comment){
        this.commentNumber = comment.getCommentNumber();
        this.commentContents = comment.getCommentContents();
        this.writeDate = comment.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
