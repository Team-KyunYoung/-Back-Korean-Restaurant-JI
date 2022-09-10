package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@ApiModel(value = "리뷰 데이터", description = "리뷰 데이타를 가진 Class")
@Entity(name = "Review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REVIEW")
public class Review {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_number")
    private Long reviewNumber;

    @ApiModelProperty(value = "리뷰 대표 이미지")
    @Column(name = "review_image")
    private String reviewImage;

    @ApiModelProperty(value = "리뷰 내용")
    @Column(name = "review_contents")
    private String reviewContents;

    @Builder
    public Review(String reviewImage, String reviewContents) {
        this.reviewImage = reviewImage;
        this.reviewContents = reviewContents;
    }
}
