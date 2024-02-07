package com.example.demo.controllers;

import com.example.demo.ServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  com.example.demo.ServiceImpl.SubmissionService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

@PostMapping("/submit/{userId}")

    public ResponseEntity<String> submitProject(@PathVariable Long userId) {
        try {
            submissionService.processSubmission(userId);
            return ResponseEntity.status(HttpStatus.OK).body("Submission processed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process submission: " + e.getMessage());
        }
    }
}
