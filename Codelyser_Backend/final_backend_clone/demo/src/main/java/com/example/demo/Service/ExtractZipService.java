package com.example.demo.Service;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExtractZipService {
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        try (ZipArchiveInputStream zipInputStream = new ZipArchiveInputStream(new FileInputStream(zipFilePath))) {
            ArchiveEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {

                if (!zipInputStream.canReadEntryData(entry)) {
                    continue;
                }

                String entryName = entry.getName();
                File entryFile = new File(destDirectory, entryName);

                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    try (FileOutputStream fos = new FileOutputStream(entryFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }
}
