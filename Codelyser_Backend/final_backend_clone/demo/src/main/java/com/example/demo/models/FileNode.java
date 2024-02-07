package com.example.demo.models;
import java.util.List;

public class FileNode {
    private String name;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    private String absolutePath;
    private List<FileNode> children;

    // Constructors, getters, and setters

    public FileNode(String name, List<FileNode> children) {
        this.name = name;
        this.children = children;
    }

    public FileNode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileNode> getChildren() {
        return children;
    }

    public void setChildren(List<FileNode> children) {
        this.children = children;
    }
}

