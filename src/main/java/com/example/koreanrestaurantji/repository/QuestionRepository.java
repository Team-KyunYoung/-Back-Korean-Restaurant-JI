package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionBoard, Long> {
    List<QuestionBoard> findByUser(User user);
    List<QuestionBoard> findByIsQNAIsTrueOrderByWriteDateDesc();
    List<QuestionBoard> findByIsQNAIsFalseOrderByWriteDateAsc();
    Optional<QuestionBoard> findByQuestionNumber(long questionNumber);

    @Transactional
    @Modifying
    @Query("update QUESTION_BOARD p set p.questionTitle = :title, p.questionContents = :contents, p.isPrivate = :isPrivate where p.questionNumber = :questionNumber")
    void updateQNAPost(long questionNumber, String title, String contents, boolean isPrivate);

    @Transactional
    @Modifying
    @Query("update QUESTION_BOARD p set p.questionTitle = :title, p.questionContents = :contents where p.questionNumber = :questionNumber")
    void updateFAQPost(long questionNumber, String title, String contents);



}
