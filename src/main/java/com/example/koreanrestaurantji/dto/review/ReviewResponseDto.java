package com.example.koreanrestaurantji.dto.review;

import com.example.koreanrestaurantji.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private final String reviewImage;
    private final String reviewContents;

    public ReviewResponseDto(Review review){
        this.reviewImage = review.getReviewImage();
        this.reviewContents = review.getReviewContents();
    }
}
