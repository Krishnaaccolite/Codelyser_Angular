package com.example.demo.Service;

import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.Repositories.SubmissionRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.ServiceImpl.SubmissionService;
import com.example.demo.ServiceImpl.UserService;
import com.example.demo.models.Question;
import com.example.demo.models.Submission;
import com.example.demo.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.EnumSet;

import static com.example.demo.Service.UserMappingService.trackMap;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ZipTheFileService zipTheFileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private RuntestService runtestService;
    @Autowired
    private WorkspaceStore workspaceStore;
    static int marks;

    public SubmissionServiceImpl()
    {

    }
    public SubmissionServiceImpl(QuestionRepository questionRepository, ZipTheFileService zipTheFileService, UserRepository userRepository, SubmissionRepository submissionRepository,RuntestService runtestService) {
        this.questionRepository = questionRepository;
        this.zipTheFileService = zipTheFileService;
        this.userRepository = userRepository;
        this.submissionRepository = submissionRepository;
        this.runtestService=runtestService;

    }
    public void processSubmission(Long userId) throws IOException {
        Long questionId = getQuestionIdForUser(userId);
        System.out.println(questionId);
        String candidateFolderPath = getCandidateFolderPath(userId);
         deleteNodeModules(candidateFolderPath);
        byte[] zipFileBytes = createZip(candidateFolderPath, userId);
        saveSubmission(userId, questionId, zipFileBytes);

//        cleanUp(candidateFolderPath,questionId);
        workspaceStore.releaseWorkSpace(userId);
    }
    @Transactional
    public Long getQuestionIdForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Question question = user.getQuestion();

        if (question == null) {
            throw new RuntimeException("Question not found for user with id: " + userId);
        }
        return question.getqId();
    }
    public String getCandidateFolderPath(Long userId) {
        return workspaceStore.getAssignedWorkspaceForCandidate(Long.valueOf(userId)).getRootDir().toString();
    }
    private void deleteNodeModules(String userFolderPath) throws IOException {
        // Assume you have a method to delete the node_modules folder
        Path nodeModulesPath = Paths.get(userFolderPath, "node_modules");
        System.out.println("NodeModulesPath: " + nodeModulesPath);
        try {
            if (Files.exists(nodeModulesPath)) {
                System.out.println("inside node modules");
                Files.walkFileTree(nodeModulesPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                    System.out.println("deleting file"+file);
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                System.out.println("node_modules folder does not exist.");
            }
        } catch (IOException e) {
            System.out.println("Error deleting node_modules: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private byte[] createZip(String candidateFolderPath, Long userId) throws IOException {
        // Assume you have a method to create a zip file
        String zipFileName = "Solution_"+userId + ".zip";
        String destinationPath = "C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_user";
        return zipTheFileService.createZip(candidateFolderPath, destinationPath, zipFileName);
    }
    private void saveSubmission(Long userId, Long questionId, byte[] zipBytes) {
            // Check if the user already has a submission for the given question
            boolean userHasSubmission = submissionRepository.existsByUser_IdAndQuestion_Id(userId, questionId);

            if (userHasSubmission) {
                // If the user already has a submission, you can handle it as needed (e.g., update, throw an exception, etc.)
                throw new RuntimeException("User already has a submission for the given question.");
            }
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        Submission submission = new Submission();
        submission.setUser(user);
        System.out.println(marks);
        submission.setScore(marks);
        String status=marks>35?"pass":"fail";
        submission.setStatus(status);
        submission.setQuestion(question);
        submission.setSubmissionDateAndTime(new Date());
        submission.setSubmittedZip(zipBytes);
        submissionRepository.save(submission);
    }
    public void cleanUp(String userFolderPath,Long questionId) throws IOException {
           String source="C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_admin\\Question_"+questionId;
           new WorkSpaceService(questionRepository).clone(source,userFolderPath);
        int cloneNumber = userFolderPath.charAt(userFolderPath.length() - 1) - '0' - 1;
        if (trackMap.containsKey(questionId)) {
            // Retrieve the Boolean array associated with the key
            Boolean[] array = trackMap.get(questionId);
            // Update the specified index
            if (cloneNumber >= 0 && cloneNumber < array.length) {
                array[cloneNumber] = true;
            } else {
                System.err.println("Index out of bounds");
            }
        } else {
            System.err.println("Key not found in the map");
        }
    }


}
