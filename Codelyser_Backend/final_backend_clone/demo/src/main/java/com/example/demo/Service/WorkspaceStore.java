package com.example.demo.Service;

import com.example.demo.Repositories.QuestionRepository;
import com.example.demo.ServiceImpl.QuestionService;
import com.example.demo.controllers.AdminUpload;
import com.example.demo.models.Workspace;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WorkspaceStore {
    @Autowired
    private QuestionRepository questionRepository;
    List<Workspace> allWorkspaces=new ArrayList<Workspace>();


    @PostConstruct
    public void initializeWorkspaces() throws IOException {
        allWorkspaces.clear();
        WorkSpaceService workSpaceService=new WorkSpaceService(questionRepository);
        Path clonedFilePath= Paths.get("C:\\Codelyser\\codelyser_user");
        List<Path> allLocalWorkSpace=getAllDirectories(clonedFilePath);
        for(Path path:allLocalWorkSpace){
            List<String> dummy = Arrays.asList(path.toString().split("\\\\"));
            List<Sting> Question=Arrays.asList(dummy.get(dummy.size()-1).split("_"));
            String clone_number = Question.get(2);
            String Question_Id=Question.get(1);
            Long clonenumber = Long.parseLong(clone_number);
            Long QuestionId=Long.parseLong(Question_Id);
            Path concatenatedPath = clonedFilePath.resolve(Paths.get(dummy.get(dummy.size()-1)));
            Workspace workspace=new Workspace(QuestionId,concatenatedPath,clonenumber);
            this.addworkspace(workspace);
        }
    }

    public void addworkspace(Workspace workspace){
        allWorkspaces.add(workspace);
        System.out.println(workspace);
    }

    public Workspace getWorkSpaceForQuestionAndCandidate(long candidateId, long questionId) {
        Workspace ws = getFreeWorkspaceForQuestion(questionId);
        assignCandidateToWorkspace(ws, candidateId);
        return ws;
    }

    public void assignCandidateToWorkspace(Workspace workspace, long candidate) {
        workspace.candidateId = candidate;
        workspace.isWorspaceAssigned = true;
        System.out.println(workspace);

    }
    public Workspace makeBackup(Long questionId,Long cloneNumber,String questionName) throws IOException {
        WorkSpaceService workSpaceService = new WorkSpaceService(questionRepository);
        Path clonedFilePath = workSpaceService.makeWorkSpace(questionId, Integer.valueOf(Math.toIntExact(cloneNumber)),questionName);
              System.out.println("in work space store "+clonedFilePath.toString());
        Workspace w=new Workspace(questionId,clonedFilePath,cloneNumber);
        addworkspace(w);
        return  w;
    }
    public void releaseWorkSpace( long candidate) {
        Workspace  workspace = getAssignedWorkspaceForCandidate(candidate);
        allWorkspaces.remove(workspace);
        System.out.println("deleted"+allWorkspaces);
    }

    public Workspace getFreeWorkspaceForQuestion(long questionID) {
        for (Workspace workspace : allWorkspaces) {
            if (workspace.questionId == questionID && !workspace.isWorspaceAssigned) {
                workspace.isWorspaceAssigned = true;
                return workspace;
            }
        }
        return null;
    }

    public Workspace getAssignedWorkspaceForCandidate(long candidateId) {

        for (Workspace workspace : allWorkspaces) {
            if (workspace.candidateId == candidateId) {
                workspace.isWorspaceAssigned = true;
                return workspace;
            }
        }
        return null;
    }


    public static List<Path> getAllDirectories(Path directory) throws IOException {
        List<Path> directories = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    directories.add(path);
                }
            }
        }
        return directories;
    }

//    public String removeWorkSpace(long userId)
//    {
//        for(Workspace workspace:allWorkspaces)
//        {
//            if(workspace.getCandidateId()==userId)
//            {
//                allWorkspaces.remove(workspace);
//                System.out.println("removed "+allWorkspaces);
//                return "removed work space.....";
//            }
//        }
//        return "work space not found ";
//    }
}
