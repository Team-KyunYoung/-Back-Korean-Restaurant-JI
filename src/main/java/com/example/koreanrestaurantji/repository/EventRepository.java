package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.EventBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventBoard, Long> {
    Optional<EventBoard> findByEventNumberOrderByWriteDateDesc(Long eventNumber);

    @Transactional
    @Modifying
    @Query("update EVENT_BOARD p set p.eventTitle = :title, p.eventImage = :image, p.eventContents = :contents where p.eventNumber = :eventNumber")
    void updatePost(long eventNumber, String title, String image, String contents);

    @Transactional
    @Modifying
    @Query("update EVENT_BOARD p set p.eventView = p.eventView+1 where p.eventNumber = :eventNumber")
    void updateViewCount(long eventNumber);
}
