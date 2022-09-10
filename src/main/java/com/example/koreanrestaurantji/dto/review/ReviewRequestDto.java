package com.example.koreanrestaurantji.dto.review;

import com.example.koreanrestaurantji.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private String reviewImage;
    private String reviewContents;

    public Review toEntity() {
        return Review.builder()
                .reviewImage(reviewImage)
                .reviewContents(reviewContents)
                .build();
    }
}
