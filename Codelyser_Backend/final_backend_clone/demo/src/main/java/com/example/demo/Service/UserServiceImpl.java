package com.example.demo.Service;

import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.ServiceImpl.UserService;
import com.example.demo.models.Question;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Override
    public User add(User user) {
        User existingUser = userRepository.findByUserEmail(user.getUserEmail());

        if (existingUser != null) {
            // If user with the email exists, return the existing user
            return existingUser;
        } else {
            // If user with the email does not exist, save the new user
            return userRepository.save(user);
        }
    }
    public User allocateQuestionId(User user) {
        if (user.getQuestion() == null) {
            // User doesn't have a QID, allocate a random one from the Question table
            Optional<Question> question = getRandomQuestionId();
            if(question.isPresent())
                user.setQuestion(question.get());
            else
                System.out.println("error occured in generating question ");
            return userRepository.save(user);
        }
        // Save the user with the allocated QID
        else
            return user;
    }
    private Optional<Question> getRandomQuestionId() {
        // Retrieve a list of all question IDs from the Question table
        List<Long> allQuestionIds = questionRepository.getAllQuestionIds();

        if (allQuestionIds.isEmpty()) {
            // Handle the case where there are no questions available
            throw new RuntimeException("No questions available");
        }
        // Choose a random question ID from the list
//        Random random = new Random();
//        int randomIndex = random.nextInt(allQuestionIds.size());
//        long id=allQuestionIds.get(randomIndex);
        Collections.sort(allQuestionIds,Collections.reverseOrder());
        System.out.println(allQuestionIds);
        return questionRepository.findById(allQuestionIds.get(0));
    }
}
