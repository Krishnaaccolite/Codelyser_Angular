package com.example.demo.Repositories;
import com.example.demo.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
    @Query("SELECT q.qId FROM Question q")
    List<Long> getAllQuestionIds();
}
