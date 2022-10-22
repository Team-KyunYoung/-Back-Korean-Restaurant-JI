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

@ApiModel(value = "이벤트 게시글 정보", description = "이벤트 게시글 데이타를 가진 Class")
@Entity(name = "EVENT_BOARD")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT_BOARD")
public class EventBoard {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_number")
    private Long eventNumber;

    @ApiModelProperty(value = "작성 날짜")
    @Column(name = "write_time", nullable = false)
    private LocalDateTime writeDate;

    @ApiModelProperty(value = "글 제목")
    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @ApiModelProperty(value = "이벤트 대표 이미지")
    @Column(name = "event_image")
    private String eventImage;

    @ApiModelProperty(value = "글 내용")
    @Column(name = "event_contents")
    private String eventContents;

    @ApiModelProperty(value = "조회수")
    @Column(name = "event_view", nullable = false)
    @ColumnDefault("0")
    private int eventView;

    @Builder
    public EventBoard(String eventTitle, String eventImage, String eventContents) {
        this.writeDate = LocalDateTime.now();
        this.eventTitle = eventTitle;
        this.eventImage = eventImage;
        this.eventContents = eventContents;
    }
}
