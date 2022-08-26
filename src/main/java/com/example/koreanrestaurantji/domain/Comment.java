package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "QNA 댓글 정보", description = "QNA 댓글 데이타를 가진 Class")
@Entity(name = "QNA_COMMENT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QNA_COMMENT")
public class Comment {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_number")
    private Long commentNumber;

    @ApiModelProperty(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", foreignKey = @ForeignKey(name = "FK_user_comment"))
    private User user;

    @ApiModelProperty(value = "QuestionBoard")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_number", foreignKey = @ForeignKey(name = "FK_question_comment"))
    private QuestionBoard questionBoard;

    @ApiModelProperty(value = "댓글")
    @Column(name = "comment_contents")
    private String commentContents;
}
