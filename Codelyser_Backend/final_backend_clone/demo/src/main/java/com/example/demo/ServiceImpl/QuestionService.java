package com.example.demo.ServiceImpl;

import com.example.demo.models.Question;

import java.util.Date;

public interface QuestionService {
    public Long setQuestion(String questionname, Date date, byte[] questionBytes);
    public Question addQuestion(String questionname, Date date, byte[] questionBytes);
}
