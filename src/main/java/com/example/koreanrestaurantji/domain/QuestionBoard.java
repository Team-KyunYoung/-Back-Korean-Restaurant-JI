package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "QNA(FNQ) 게시글 리스트", description = "질문 게시글 정보를 가진 Class")
@Entity(name = "QUESTION_BOARD")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUESTION_BOARD")
public class QuestionBoard {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_number")
    private Long questionNumber;

    @ApiModelProperty(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", foreignKey = @ForeignKey(name = "FK_user_question"))
    private User user;

    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL, targetEntity=Comment.class)
    private List<Comment> comments = new ArrayList<>();

    @ApiModelProperty(value = "작성 날짜")
    @Column(name = "write_time", nullable = false)
    private LocalDateTime writeDate;

    @ApiModelProperty(value = "글 제목")
    @Column(name = "question_title")
    private String questionTitle;

    @ApiModelProperty(value = "글 내용")
    @Column(name = "question_contents", length = 500)
    private String questionContents;

    // QNA or FNQ
    @ApiModelProperty(value = "True=QNA, False=FNQ")
    @Column(name = "is_qna", nullable = false)
    @ColumnDefault("true")
    private boolean isQNA;

    // 게시글 공개or비공개
    @ApiModelProperty(value = "True=private, False=public")
    @Column(name = "is_private", nullable = false)
    @ColumnDefault("false")
    private boolean isPrivate;

    @Builder
    public QuestionBoard(User user, String questionTitle, String questionContents, boolean isQNA, boolean isPrivate) {
        this.user = user;
        this.writeDate = LocalDateTime.now();
        this.questionTitle = questionTitle;
        this.questionContents = questionContents;
        this.isQNA = isQNA;
        this.isPrivate = isPrivate;
    }
}
