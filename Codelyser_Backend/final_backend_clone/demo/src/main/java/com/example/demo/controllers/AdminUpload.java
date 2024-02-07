package com.example.demo.controllers;

import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Service.*;
import com.example.demo.ServiceImpl.QuestionService;
import com.example.demo.models.Question;
import com.example.demo.models.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:4200/upload-file")
public class AdminUpload {
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    WorkspaceStore workspaceStore;
    static public Long questionId;

   static public String question_name;

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("questionname") String questionname) {
        String uploadDirectory = "C:\\Codelyser\\codelyser_admin";
        QuestionRepository Question;
        try {
            ZipTheFileService zip = new ZipTheFileService();
            zip.zipthefile(uploadDirectory, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
        try {
            Path filePath = Path.of(uploadDirectory, file.getOriginalFilename());
            byte[] zipBytes = Files.readAllBytes(filePath);
            questionId = questionService.setQuestion(questionname, new Date(), zipBytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file: in database " + e.getMessage());
        }
        try {
            ExtractZipService extractZip = new ExtractZipService();
            question_name =  questionname+"_"+questionId;
            Path extractDirectory = Path.of(uploadDirectory, question_name);
            WorkSpaceService workSpaceService = new WorkSpaceService(questionRepository);
            Files.createDirectories(extractDirectory);
            Path filePath = Path.of(uploadDirectory, file.getOriginalFilename());
            System.out.println(filePath.toString());
            System.out.println(extractDirectory.toString());
            extractZip.unzip(filePath.toString(), extractDirectory.toString());
            for (int i = 0; i < 3; i++) {
                Path clonedFilePath = workSpaceService.makeWorkSpace(questionId,i + 1,questionname);
                System.out.println(clonedFilePath);
                Workspace w=new Workspace(questionId,clonedFilePath,i+1);
                workspaceStore.addworkspace(w);
            }
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}