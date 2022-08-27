package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import com.example.koreanrestaurantji.dto.question.QuestionPostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionBoard, Long> {
    List<QuestionBoard> findByIsQNAIsTrueOrderByWriteDateDesc();
    List<QuestionBoard> findByIsQNAIsFalseOrderByWriteDateAsc();
    Optional<QuestionBoard> findByQuestionNumber(long questionNumber);

    @Transactional
    @Modifying  //(clearAutomatically = true) // bulk연산이 실행된 후 1차캐시를 비워주는 속성. default=false
    @Query("update QUESTION_BOARD p set p.questionTitle = :title, p.questionContents = :contents, p.isPrivate = :isPrivate where p.questionNumber = :questionNumber")
    void updateQNAPost(long questionNumber, String title, String contents, boolean isPrivate);

    @Transactional
    @Modifying  //(clearAutomatically = true)
    @Query("update QUESTION_BOARD p set p.questionTitle = :title, p.questionContents = :contents where p.questionNumber = :questionNumber")
    void updateFNQPost(long questionNumber, String title, String contents);



}
