package com.vmd.vmdwebshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class imageService {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName;
        Path filePath;
        Random random = new Random();
        do {
            int randomNumber = 10000 + random.nextInt(90000);
            fileName = "image_" + randomNumber + ".png";
            filePath = uploadPath.resolve(fileName);
        } while (Files.exists(filePath));

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }
}