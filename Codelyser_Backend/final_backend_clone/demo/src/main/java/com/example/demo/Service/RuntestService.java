package com.example.demo.Service;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RuntestService {
    double marks;
    public ArrayList<String> output = new ArrayList<>();
    public static String Result;
    public String ddd;

    public void setMarks(double marks) {
        this.marks = marks;
    }

    String result;
    String totalSuccess;
    String totalFailed;

    public boolean startTest(String angularProjectDirectory) throws IOException, InterruptedException {
        int testExitCode = 1;

        try {
            String testCommand = "ng test";
            ProcessBuilder testProcessBuilder = new ProcessBuilder("cmd", "/c", testCommand);
            testProcessBuilder.redirectErrorStream(true);
                        testProcessBuilder.directory(new File(angularProjectDirectory));
            System.out.println("running testcases.........");

            // Start the process
Process testProcess = testProcessBuilder.start();
            // Read the output of the test process
            BufferedReader testReader = new BufferedReader(new InputStreamReader(testProcess.getInputStream()));
            String line;

            while ((line = testReader.readLine()) != null) {
                System.out.println(line);
                output.add(line);
                if (line.contains("TOTAL:")) {
                    testExitCode = 0;
                    break;
                }
            }
            if(output.size()>0) {
                Result = output.get(output.size()-1);
            }
            System.out.println("came out of loop");
            if(output.size()>0) {
                ddd = output.get(output.size() - 1);
            }
            System.out.println("last line "+ddd);
            String regex = "TOTAL: (\\d+) SUCCESS(?:, (\\d+) FAILED)?";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(ddd);
            if (matcher.find()) {
                totalSuccess = matcher.group(1) != null ? matcher.group(1) : "0";
                totalFailed = matcher.group(2) != null ? matcher.group(2) : "0";
                System.out.println("Success Variable: " + totalSuccess);
                System.out.println("Failed Variable: " + totalFailed);
                marks+=(100/6)*Double.valueOf(totalSuccess);
                setMarks(marks);
                System.out.println(marks);
            }
            else {
                System.out.println("pattern doesn't match");
            }
            System.out.println("Test of Angular process exited with code: " + testExitCode);

            if (testExitCode == 0) {
                System.out.println("Angular Project Test Passed Successfully");
            } else {
                System.out.println("Angular Project Test Failed");
            }

            testProcess.destroyForcibly();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testExitCode == 0;
    }
    public double getMarks()
    {
        return marks;
    }
    public String ouputString() {
        StringBuilder outputString = new StringBuilder();
        for (String s : output) {
            result = s;
            outputString.append(s + "\n");
        }
        System.out.println(getMarks());
        SubmissionServiceImpl.marks=(int)getMarks();
        return outputString.toString();
    }
}
