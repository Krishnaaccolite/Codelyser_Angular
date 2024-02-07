package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipTheFileService {
    public void zipthefile(String uploadDirectory, MultipartFile file) throws IOException {
        Files.createDirectories(Path.of(uploadDirectory));
        Path filePath = Path.of(uploadDirectory, file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }
    public byte[] createZip(String folderPath, String destinationPath, String zipFileName) throws IOException {
        Path sourceFolderPath = Paths.get(folderPath);
        Path zipFilePath = Paths.get(destinationPath, zipFileName);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(baos)) {

            Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path relativePath = sourceFolderPath.relativize(file);
                    //skipping large/unnecessary folders like .angular
                    if (!relativePath.toString().contains(".angular")) {
                        System.out.println("Adding to zip: " + relativePath);

                        ZipEntry zipEntry = new ZipEntry(relativePath.toString());
                        zipOutputStream.putNextEntry(zipEntry);
                        Files.copy(file, zipOutputStream);
                        zipOutputStream.closeEntry();
                    } else {
                        System.out.println("Skipping: " + relativePath);
                    }

                    return FileVisitResult.CONTINUE;
                }
            });

            // Close the ZipOutputStream before writing to the file
            zipOutputStream.close();

            // Write the zip file to the specified location
            Files.write(zipFilePath, baos.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return baos.toByteArray();
        }
    }
}
