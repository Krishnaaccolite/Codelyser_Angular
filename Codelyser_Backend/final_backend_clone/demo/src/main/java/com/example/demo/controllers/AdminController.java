package com.example.demo.controllers;
import com.example.demo.models.Question;
import com.example.demo.models.User;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/{userId}/assign-question/{qId}")
    public ResponseEntity<String> assignQuestionToUser(@PathVariable Long userId, @PathVariable Long qId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            Optional<Question> optionalQuestion = questionRepository.findById(qId);

            if (optionalUser.isPresent() && optionalQuestion.isPresent()) {
                User user = optionalUser.get();
                Question question = optionalQuestion.get();

                // Assign the question to the user
                user.setQuestion(question);
                userRepository.save(user);

                return ResponseEntity.status(HttpStatus.OK).body("Question assigned successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Question not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign question: " + e.getMessage());
        }
    }
}