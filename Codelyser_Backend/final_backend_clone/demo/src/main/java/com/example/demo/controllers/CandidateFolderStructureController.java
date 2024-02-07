package com.example.demo.controllers;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Service.UserMappingService;
import com.example.demo.Service.WorkspaceStore;
import com.example.demo.models.FileNode;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/candidate/project-structure/{userId}")
@CrossOrigin(origins = "http://localhost:4200")
public class CandidateFolderStructureController {
    @Autowired
    WorkspaceStore workspaceStore;
    @Autowired
    UserRepository userRepository;
    public static String projectPath; // Use double backslashes or use forward slashes
//    int counter=0;
//    String prevUserId="";\
    @GetMapping()
    public ResponseEntity<List<FileNode>> getProjectStructure(@PathVariable String userId) {
        System.out.println("file explorer "+userId);
        projectPath= workspaceStore.getAssignedWorkspaceForCandidate(Long.valueOf(userId)).getRootDir().toString();
        System.out.println(projectPath);
        File srcDirectory = new File(projectPath, "src");
        File readmeFile = new File(projectPath, "README.md");

        try {
            List<FileNode> children = new ArrayList<>();
            if (srcDirectory.exists() && srcDirectory.isDirectory()) {
                System.out.println("yes source is there ");
                FileNode srcNode = new FileNode();
                srcNode.setName("src");
                srcNode.setChildren(getFiles(srcDirectory));
                children.add(srcNode);
            }
            if (readmeFile.exists() && readmeFile.isFile()) {
                FileNode readmeNode = new FileNode();
                readmeNode.setName("README.md");
                children.add(readmeNode);
            }

            return new ResponseEntity<>(children, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<FileNode> getFiles(File directory) {
        List<FileNode> files = new ArrayList<>();

        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                FileNode fileNode = new FileNode();
                fileNode.setName(file.getName());

                if (file.isDirectory()) {
                    fileNode.setChildren(getFiles(file));
                }

                files.add(fileNode);
            }
        }

        return files;
    }


    @PostMapping("/explorer/file-content")
    public ResponseEntity<Map<String, String>> getAllFileContent(@RequestBody Map<String, String> requestData) {
        String filePath = requestData.get("filepath");

        try {
            System.out.println(projectPath);
            Path fullPath = Paths.get(projectPath, filePath);
            System.out.println(fullPath);
            File file = fullPath.toFile();

            if (!file.exists() || !file.isFile()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String content = new String(Files.readAllBytes(file.toPath()));
            Map<String, String> response = Collections.singletonMap(file.getName(), content);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("content", "Error reading file content."));
        }
    }
    @GetMapping("/user")
    public ResponseEntity<String> getProjectNameByUserId(@PathVariable Long userId) {
        // Check if the user exists
        User user = userRepository.findById(userId).orElse(null);

        if (user != null && user.getQuestion().getQuestionname()!=null) {
            // User exists and is associated with a project
            String projectName = user.getQuestion().getQuestionname();
            return ResponseEntity.ok(projectName);
        } else {
            // User not found or not associated with any project
            return ResponseEntity.notFound().build();
        }
    }
}
