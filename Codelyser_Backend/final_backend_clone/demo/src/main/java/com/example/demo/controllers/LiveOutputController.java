package com.example.demo.controllers;
import com.example.demo.Service.StoreAndCompile;
import com.example.demo.Service.UserMappingService;
import com.example.demo.Service.WorkspaceStore;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
@RestController
public class LiveOutputController {
    private String rootFolder;
    @Autowired
    WorkspaceStore workspaceStore;
    @RequestMapping(value = "/live-output/{unique-user-code}/{file-name}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("unique-user-code") String uniqueUserCode,
            @PathVariable("file-name") String fileName,
            HttpServletResponse response) {

        if (fileName == null || fileName.isBlank()) {
            fileName = "index.html";
        }
        if (uniqueUserCode == null || uniqueUserCode.isBlank()) {
            uniqueUserCode = "1";
        }
        Path dest= findbrowserFolder((new File(rootFolder))).toPath();
        try (FileInputStream is = new FileInputStream(new File(dest.toString(), fileName))) {
            if (fileName.endsWith("js")) {
                response.setHeader("Content-type", "application/javascript");
            }
            if (fileName.endsWith("css")) {
                response.setHeader("Content-type", "text/css; charset=utf-8");
            }

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
            //log.error("File not found: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException ex) {
            System.out.println("file not found");
            //log.error("Error writing file to output stream: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    private static File findbrowserFolder(File directory) {
        // Check if the current directory has a folder named "src"
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory() && "browser".equals(file.getName())) {
                    return file; // Found the 'src' folder
                } else if (file.isDirectory()) {
                    File result = findbrowserFolder((file)); // Recursively search in subdirectories
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null; // 'src' folder not found in this directory
    }
    @RequestMapping(value = "/live-output/{userId}/{file-name}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> getFile(
            @PathVariable("userId") String userId,
            @RequestBody Map<String, String> map,
            HttpServletResponse response) {
        try {
            String filename = "";
            String candidatecode = "";
            rootFolder = workspaceStore.getAssignedWorkspaceForCandidate(Long.valueOf(userId)).getRootDir().toString();
            StoreAndCompile storeAndCompile = new StoreAndCompile();
            System.out.println("Live Server Entry");
            System.out.println(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("MapEntry");
                filename = entry.getKey();
                candidatecode = entry.getValue();
                System.out.println("filename " + filename);
                System.out.println("CandidateCode " +candidatecode);
                if(filename!=null && candidatecode!=null) {
                    storeAndCompile.storeInTsFile(candidatecode, rootFolder, filename);
                }
                System.out.println("MapEnding");
            }
            storeAndCompile.npminstall(rootFolder);
            storeAndCompile.checkbuilddirexist(rootFolder);
            boolean buildStatus = storeAndCompile.startBuild(rootFolder);
            if (!buildStatus) {
                return ResponseEntity.ok(Map.of("response", storeAndCompile.error));
            }
        } catch (Exception e) {
            response.resetBuffer();
            System.out.println("this catch block ");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("response", "Error Occurred In LiveServer"));
        }

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("response", "okkh");
        return ResponseEntity.status(500).body(map1);
    }
}
