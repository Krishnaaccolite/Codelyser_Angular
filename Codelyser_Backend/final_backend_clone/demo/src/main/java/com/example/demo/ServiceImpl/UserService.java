package com.example.demo.ServiceImpl;

import com.example.demo.models.User;

public interface UserService {
    public User add(User user);
    public User allocateQuestionId(User user);
}
