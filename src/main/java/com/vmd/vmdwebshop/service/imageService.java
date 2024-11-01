package com.vmd.vmdwebshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class imageService {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            fileName = "image.png"; // Default filename
        } else {
            String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
            String uniqueIdentifier;
            Path filePath;
            do {
                uniqueIdentifier = System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
                fileName = uniqueIdentifier + fileExtension;
                filePath = uploadPath.resolve(fileName);
            } while (Files.exists(filePath));
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }
}