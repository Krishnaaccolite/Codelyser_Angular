package com.example.demo.models;

import java.nio.file.Path;

public class Workspace {

    String workspaceId;
    public long questionId;
    long workspaceNumber;
    public boolean isWorspaceAssigned = false;
   public long candidateId = 0;
   public Path rootDir;

    @Override
    public String toString() {
        return "Workspace{" +
                "workspaceId='" + workspaceId + '\'' +
                ", questionId=" + questionId +
                ", workspaceNumber=" + workspaceNumber +
                ", isWorspaceAssigned=" + isWorspaceAssigned +
                ", candidateId=" + candidateId +
                ", rootDir=" + rootDir +
                '}';
    }

    public Workspace(long questionId, Path rootDir, long workspaceNumber) {
        this.questionId = questionId;
        this.rootDir = rootDir;
        this.workspaceNumber=workspaceNumber;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getWorkspaceNumber() {
        return workspaceNumber;
    }

    public void setWorkspaceNumber(long workspaceNumber) {
        this.workspaceNumber = workspaceNumber;
    }

    public boolean isWorspaceAssigned() {
        return isWorspaceAssigned;
    }

    public void setWorspaceAssigned(boolean worspaceAssigned) {
        isWorspaceAssigned = worspaceAssigned;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }

    public Path getRootDir() {
        return rootDir;
    }

    public void setRootDir(Path rootDir) {
        this.rootDir = rootDir;
    }
}
