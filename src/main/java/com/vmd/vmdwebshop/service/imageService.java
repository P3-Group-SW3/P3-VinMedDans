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

/**
 * Service for handling image uploads and retrievals
 */
@Service
public class imageService {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    /**
     * Save an image
     * @param file The image file
     * @param customName The custom name for the image
     * @return The path to the saved image
     * @throws IOException If an error occurs while saving the image
     */
    public String saveImage(MultipartFile file, String customName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName;
        if (customName != null && !customName.isEmpty()) {
            fileName = customName + ".png";
        } else {
            Random random = new Random();
            int randomNumber = 10000 + random.nextInt(90000);
            fileName = "image_" + randomNumber + ".png";
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }
}