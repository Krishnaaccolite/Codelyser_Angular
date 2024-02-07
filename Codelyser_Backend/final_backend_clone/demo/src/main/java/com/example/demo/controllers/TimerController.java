package com.example.demo.controllers;

import com.example.demo.ServiceImpl.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/timer")
public class TimerController {
    @Autowired
    private SubmissionService submissionService;
    private static long timer = 7200; // 2 hours in seconds

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateTimer(@RequestBody Map<String, Long> timerMap,@PathVariable Long userId) {
        timer = timerMap.get("timerValue");
        if(timer==1)
        {
            try {
                submissionService.processSubmission(userId);
                return ResponseEntity.status(HttpStatus.OK).body("Submission processed successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process submission: " + e.getMessage());
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Long> getTimer() {
        return ResponseEntity.ok(timer);
    }

}