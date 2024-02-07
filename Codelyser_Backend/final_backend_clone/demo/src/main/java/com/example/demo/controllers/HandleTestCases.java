package com.example.demo.controllers;

import com.example.demo.Service.PdfService;
import com.example.demo.Service.RuntestService;
import com.example.demo.Service.UserMappingService;
import com.example.demo.Service.WorkspaceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@RestController
public class HandleTestCases {
    @Autowired
    WorkspaceStore workspaceStore;
    String projectPath ; // Use double backslashes or use forward slashes
//    int counter=0;
//    String prevUserId="";
    @PostMapping("/runtests/{userId}")
    public ResponseEntity<Map<String, ArrayList<String>>> runTests(@PathVariable String userId) throws IOException, InterruptedException {
//        String projectPath = "C:\\Users\\immadisetty.deepika\\codelyser\\codelyser_user";
//        if(counter>0)
//        {
//            projectPath = projectPath.replaceAll("\\\\Solution_"+prevUserId, "");
//            System.out.println("replaced path "+projectPath);
//        }
//        prevUserId=userId;
//        counter++;
//        projectPath+="\\Solution_" + userId;
//        projectPath= UserMappingService.paths.get(Long.valueOf(userId));
//        System.out.println(projectPath);
        projectPath = workspaceStore.getAssignedWorkspaceForCandidate(Long.valueOf(userId)).getRootDir().toString();
        System.out.println(projectPath);
//        StoreAndComplieService storeAndComplie=new StoreAndComplieService();
//        String specsfile=specsdata.get("testCode");
//        storeAndComplie.storeInTsFile(specsfile,"C:\\Users\\immadisetty.deepika\\demo_addition\\src\\app\\app.component.spec.ts");
        RuntestService r=new RuntestService();
        boolean flag= r.startTest(projectPath);
        String result= r.ddd;
        PdfService pdfService=new PdfService();
        pdfService.generatePdf(r.ouputString(),projectPath,result);
        if(flag)
            return ResponseEntity.ok(Map.of("testResponse",r.output));
        else {
            ArrayList<String> response =new ArrayList<>(Arrays.asList("failure"));
            return ResponseEntity.ok(Map.of("testResponse", response));
        }
    }
}
