//package com.example.demo.Service;
//
//import com.example.demo.Repositories.QuestionRepository;
//import com.example.demo.Repositories.UserRepository;
//import com.example.demo.models.Question;
//import com.example.demo.models.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.Optional;
//
//@Service
//public class CloneService {
////    private  QuestionRepository questionRepository;
//    private UserRepository userRepository;
////    @Autowired
////    public CloneService(QuestionRepository questionRepository) {
////        this.questionRepository = questionRepository;
////        this.questionRepository=null;
////    }
//    @Autowired
//    public CloneService( UserRepository userRepository)
//    {
//        this.userRepository=userRepository;
//    }
//
//
//    //    @Autowired
////    public CloneService(UserRepository userRepository) {
////        this.userRepository = userRepository;
////    }
////    @Autowired
////    public  CloneService(QuestionRepository questionRepository, UserRepository userRepository)
////    {
////            this.questionRepository=questionRepository;
////        this.userRepository = userRepository;
////    }
//    public Path cloneQuestion(Long userId) throws IOException {
//        System.out.println("entered");
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//            System.out.println("User found: " + user.get().getUserId());
//        } else {
//            System.out.println("User not found for userId: " + userId);
//            return null;
//        }
//        String sourcePath="C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_admin\\";
//        String destPath="C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_user";
//        sourcePath+="Question_";
//        if(user.isPresent())
//        {
//            sourcePath+=user.get().getQuestion().getqId();
//            System.out.println(sourcePath);
//        }
//        else
//            return null;
//        String solutionName = "Solution_"+userId;
//        Path extractDirectory = Path.of(destPath,solutionName);
//        if (Files.exists(extractDirectory)) {
//            System.out.println("Directory already exists: " + extractDirectory);
//            return extractDirectory;  // Exit the method or class as the directory is already present
//        }
//        else {
//            Files.createDirectories(extractDirectory);
//            System.out.println(extractDirectory.toString());
//            clone(sourcePath, extractDirectory.toString());
//            return Path.of(destPath, solutionName);
//        }
//    }
////    public Path makeWorkSpace(Long questionId,int cloneNumber) throws IOException {
////        System.out.println("entered");
////        Optional<Question> question= questionRepository.findById(questionId);
////        if (question.isPresent()) {
////            System.out.println("Question found: " + question.get().getqId());
////        } else {
////            System.out.println("Question not found for userId: " + questionId);
////            return null;
////        }
////        String sourcePath="C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_admin\\";
////        String destPath="C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_user";
////        sourcePath+="Question_";
////        if(question.isPresent())
////        {
////            sourcePath+=question.get().getqId();
////            System.out.println(sourcePath);
////        }
////        else
////            return null;
////        String solutionName = "Solution_"+question.get().getqId()+"_"+cloneNumber;
////        Path extractDirectory = Path.of(destPath,solutionName);
////        if (Files.exists(extractDirectory)) {
////            System.out.println("Directory already exists: " + extractDirectory);
////            return extractDirectory;  // Exit the method or class as the directory is already present
////        }
////        else {
////            Files.createDirectories(extractDirectory);
////            System.out.println(extractDirectory.toString());
////            clone(sourcePath, extractDirectory.toString());
////            return Path.of(destPath, solutionName);
////        }
////    }
//    public void clone(String sourceDirectory, String destinationDirectory) throws IOException {
//        File srcFolder = findSrcFolder(new File(sourceDirectory));
//        File parentDirectory = srcFolder.getParentFile();
//        Path parentPath= Path.of(parentDirectory.getPath());
//        System.out.println("source :"+parentDirectory+" destination :"+destinationDirectory);
//        performClone(parentPath,Path.of(destinationDirectory));
//    }
//
//    private void performClone(Path sourceDirectory, Path clonedDirectory) throws IOException {
//        Files.walk(sourceDirectory)
//                .forEach(source -> {
//                    Path destination = clonedDirectory.resolve(sourceDirectory.relativize(source));
//                    try {
//                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }
//
//    private static File findSrcFolder(File directory) {
//        // Check if the current directory has a folder named "src"
//        File[] contents = directory.listFiles();
//        if (contents != null) {
//            for (File file : contents) {
//                if (file.isDirectory() && "src".equals(file.getName())) {
//                    return file; // Found the 'src' folder
//                } else if (file.isDirectory()) {
//                    File result = findSrcFolder(file); // Recursively search in subdirectories
//                    if (result != null) {
//                        return result;
//                    }
//                }
//            }
//        }
//        return null; // 'src' folder not found in this directory
//    }
//}
