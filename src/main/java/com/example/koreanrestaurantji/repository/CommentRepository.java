package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Comment;
import com.example.koreanrestaurantji.domain.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentNumber(Long commentNumber);
    List<Comment> findByQuestionBoard(QuestionBoard questionBoard);
    List<Comment> findByQuestionBoardOrderByWriteDateDesc(QuestionBoard questionBoard);

    @Transactional
    @Modifying
    @Query("update QNA_COMMENT c set c.commentContents = :comment  where c.commentNumber = :commentNumber")
    void updateComment(long commentNumber, String comment);
}
