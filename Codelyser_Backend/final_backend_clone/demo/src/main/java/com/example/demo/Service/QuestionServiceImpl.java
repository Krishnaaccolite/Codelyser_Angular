package com.example.demo.Service;

import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.ServiceImpl.QuestionService;
import com.example.demo.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public Question addQuestion(String questionname, Date date, byte[] questionBytes)
    {
        Question question=new Question(questionname, date,questionBytes);

        return questionRepository.save(question);
    }

    public Long setQuestion(String questionname, Date date, byte[] questionBytes) {
        try {
            Question question = new Question();
            question.setQuestion(questionname, date, questionBytes);
            System.out.println(question.getQuestionname() + " " + question.getDateTime());
            Question resultQuestion=questionRepository.save(question);
            return resultQuestion.getqId();
        } catch (Exception e) {
            // Handle the exception, log it, or rethrow a custom exception
            e.printStackTrace();
        }
        return null;
    }
}
