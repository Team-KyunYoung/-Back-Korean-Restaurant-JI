package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    @Query("update Review r set r.reviewImage = :image, r.reviewContents = :contents where r.reviewNumber = :reviewNumber")
    void updateReview(long reviewNumber, String image, String contents);

    Optional<Review> findByReviewNumber(long reviewNumber);
}
