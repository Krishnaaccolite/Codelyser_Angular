package com.example.demo.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class StoreAndCompile {
    public String error = "";


    public boolean npminstall(String rootFolderPath) {
        int startExitCode = 1;
        try {
            String angularProjectDirectory = rootFolderPath;
            System.out.println(angularProjectDirectory);
            String buildcommand = "npm install";
            ProcessBuilder startProcessBuilder = new ProcessBuilder("cmd", "/c", buildcommand);
            startProcessBuilder.directory(new File(angularProjectDirectory));
            startProcessBuilder.redirectErrorStream(true);
            // Start the process
            Process startProcess = startProcessBuilder.start();
            // Read the output of the start process
            BufferedReader startReader = new BufferedReader(new InputStreamReader(startProcess.getInputStream()));
            startExitCode = startProcess.waitFor();
            System.out.println("Npm Install Process exited with code: " + startExitCode);
            if (startExitCode == 0) {
                System.out.println("Npm install for Angular project is successfully");
            } else {
                System.out.println("Npm install for Angular project is Unsuccessfully");
            }
            startProcess.destroyForcibly();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return startExitCode==0;
    }

    public void storeInTsFile(String content,String rootfolderpath,String filename) {
//        try {
        // Specify the path to the TypeScript file in the "resources" directory
        System.out.println("Changing "+ filename);
        String resourcesPath = rootfolderpath+"\\src\\app\\";
        String filePath = resourcesPath + filename;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Files.write(Paths.get(filePath), content.getBytes());
                System.out.println("File updated: " + filePath);
            } catch (Exception e) {
                System.err.println("Error updating file: " + e.getMessage());
            }
        }, executorService);

        // Shutdown the ExecutorService to allow the application to exit
        executorService.shutdown();
        // Wait for the asynchronous operation to complete
        future.join();
    }

    public boolean checkbuilddirexist(String rootFolderPath) {
        try {
            String directoryPath = rootFolderPath+"\\dist";
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                try {
                    System.out.println("Yes Directory already exist");
//                    checklocalhost();
                    // Build the command to delete the directory
                    String os = System.getProperty("os.name").toLowerCase();
                    String command = "";

                    // Windows command
                    command = "cmd /c rmdir /s /q \"" + directoryPath + "\"";
//                    }

                    // Build the process
                    ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                    processBuilder.redirectErrorStream(true);

                    // Start the process
                    Process process = processBuilder.start();

                    // Wait for the process to exit
                    int exitCode = process.waitFor();
                    System.out.println("Build folder delete succesfully exited with code: " + exitCode);
                    return true;
                    // Optionally handle the exit code or process the output
                } catch (IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Directory is Not present");
        return false;
    }
    public boolean startBuild(String rootFolderPath) throws IOException, InterruptedException {
        int startExitCode=1;
        try{
            String angularProjectDirectory = rootFolderPath;
            String buildcommand = "ng build";
            ProcessBuilder startProcessBuilder = new ProcessBuilder("cmd", "/c", buildcommand);
            startProcessBuilder.directory(new File(angularProjectDirectory));
            startProcessBuilder.redirectErrorStream(true);
            // Start the process
            Process startProcess = startProcessBuilder.start();
            // Read the output of the start process
            BufferedReader startReader = new BufferedReader(new InputStreamReader(startProcess.getInputStream()));
            String line;
            while ((line = startReader.readLine()) != null) {
                error+=line;
                error+="\n";
//                System.out.println(line);
            }
            System.out.println(error);
            startExitCode = startProcess.waitFor();
            System.out.println("Build of Angular process exited with code: " + startExitCode);
            if(startExitCode==0){
                System.out.println("Angular Project Build Succesfully");
            }
            else{
                System.out.println("Angular Project Build Unsuccesfully");
            }
            startProcess.destroyForcibly();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return startExitCode==0;
    }
    public File findFileInDirectory(File directory, String fileName) {
        // Check if the current directory contains the specified file
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory()) {
                    File result = findFileInDirectory(file, fileName); // Recursively search in subdirectories
                    if (result != null) {
                        return result;
                    }
                } else if (fileName.equals(file.getName())) {
                    return file; // Found the specified file
                }
            }
        }
        return null; // Specified file not found in this directory
    }
}