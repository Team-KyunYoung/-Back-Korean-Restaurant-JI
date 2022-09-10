package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Review;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.review.ReviewRequestDto;
import com.example.koreanrestaurantji.dto.review.ReviewResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.ReviewRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public Boolean findUserByTokenIsRole(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND)).isRole();
    }

    public SuccessResponseDto create(ReviewRequestDto reviewRequestDto) {
        if(findUserByTokenIsRole()) {
            try {
                reviewRepository.save(reviewRequestDto.toEntity());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_REVIEW);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    public SuccessResponseDto updateReview(long reviewNumber, ReviewRequestDto reviewRequestDto){
        if(findUserByTokenIsRole()) {
            try {
                reviewRepository.updateReview(reviewNumber,
                        reviewRequestDto.getReviewImage(),
                        reviewRequestDto.getReviewContents());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_REVIEW);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(Long reviewNumber){
        if(findUserByTokenIsRole()) {
            try {
                Review review = reviewRepository.findByReviewNumber(reviewNumber).orElseThrow(() -> new BaseException(BaseResponseCode.REVIEW_NOT_FOUND));
                reviewRepository.delete(review);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
