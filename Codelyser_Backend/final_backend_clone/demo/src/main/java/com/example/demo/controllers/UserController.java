package com.example.demo.controllers;
import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Service.UserMappingService;
import com.example.demo.Service.WorkspaceStore;
import com.example.demo.ServiceImpl.UserService;
import com.example.demo.models.Question;
import com.example.demo.models.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkspaceStore workspacestore;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/add")
    public User addUser(@RequestBody User user)
    {
        return userService.add(user);
    }
    @GetMapping("/{userId}/makeclone")
    public ResponseEntity<String> cloneFile(@PathVariable Long userId) {
        try {
                if(userRepository.existsById(userId)) {
                   Optional<User> user=userRepository.findById(userId);
                    User newUser = userService.allocateQuestionId(user.get());
                    Long qid=newUser.getQuestion().getqId();
                    Question question = questionRepository.findById(qid).orElse(null);
                    String questionName=question.getQuestionname();
                    workspacestore.getWorkSpaceForQuestionAndCandidate(newUser.getUserId(),newUser.getQuestion().getqId());
                    System.out.println(workspacestore);
                    Long cloneNumber=workspacestore.getAssignedWorkspaceForCandidate(userId).getWorkspaceNumber();
                    workspacestore.makeBackup(qid,cloneNumber+3,questionName);
//                    UserMappingService userMappingService=new UserMappingService();
//                    userMappingService.insertInMap(userId,user.get().getQuestion().getqId());
//                    System.out.println(userMappingService.getFilePath(userId));
                }
                else
                {
                    System.out.println("user is not existing ");
                }
            return ResponseEntity.status(HttpStatus.OK).body("File cloned successfully" );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clone file: " + e.getMessage());
        }
    }
}
