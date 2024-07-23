package com.datien.booknetwork.file;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull Integer userId
    ) {
        // ex: users/1
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    public String uploadFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String fileUploadSubPath
    ) {
        final String finalUploadFile = fileUploadPath + separator + fileUploadSubPath;
        File targerFile = new File(finalUploadFile);
        if(!targerFile.exists()) {
            boolean createFolder = targerFile.mkdirs();
            if(!createFolder) {
                log.warn("Failed to create the target folder.");
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        // ./upload/users/1/21242.jpg
        String targetFilePath = fileUploadPath + separator + currentTimeMillis() + "." + fileExtension;

        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to {}", targetFilePath);
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return "";
    }

    private String getFileExtension(String fileName) {

        if(fileName == null || fileName.isEmpty()) {
            return "";
        }

        // example: fileName.jpg or fileName.png (jpg, png is the file extension)
        int lastDotIndex = fileName.lastIndexOf(".");
        if(lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

}
