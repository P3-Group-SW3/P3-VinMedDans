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
            if (customName.contains("..") || customName.contains("/") || customName.contains("\\")) {
                throw new IllegalArgumentException("Invalid custom name");
            }
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

    /**
     * Get a list of images
     * @return A list of image file names
     */
    public <List>String getImages() {
        Path path = Paths.get(uploadDir);
        return path.toString();
    }

    /**
     * Delete an image
     * @param filename The name of the image file
     * @return True if the image was deleted, false otherwise
     * @throws IOException If an error occurs while deleting the image
     */
    public boolean deleteImage(String filename) throws IOException {
        Path path = Paths.get(uploadDir).resolve(filename);
        return Files.deleteIfExists(path);
    }
}