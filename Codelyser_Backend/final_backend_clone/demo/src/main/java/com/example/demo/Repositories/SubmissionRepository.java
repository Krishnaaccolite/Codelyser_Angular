package com.example.demo.Repositories;

import com.example.demo.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubmissionRepository  extends JpaRepository<Submission, Long> {
    @Query("SELECT COUNT(s) > 0 FROM Submission s WHERE s.user.userId = :userId AND s.question.qId = :questionId")
    boolean existsByUser_IdAndQuestion_Id(@Param("userId") Long userId, @Param("questionId") Long questionId);

}
