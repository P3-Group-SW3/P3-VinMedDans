package com.vmd.vmdwebshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ImageService {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    /**
     * Saves the image to the upload directory
     * @param file the image file
     * @param customName the custom name of the image
     * @return the path to the saved image
     * @throws IOException if an I/O error occurs
     */
    public String saveImage(MultipartFile file, String customName) throws IOException {
        if (uploadDir == null) {
            throw new IllegalStateException("uploadDir is not set");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName;
        if (customName != null && !customName.isEmpty()) {
            if (customName.contains("..") || customName.contains("/") || customName.contains("\\")) { // Prevent file names which can cause issues in the file system
                throw new IllegalArgumentException("Invalid custom name");
            }

            fileName = customName + ".png";
        } else {
            Random random = new Random(); // Randomized name
            int randomNumber = 10000 + random.nextInt(90000);
            fileName = "image_" + randomNumber + ".png";
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }

    /**
     * Gets a list of all image names in the upload directory
     * @return a list of image names
     * @throws IOException if an I/O error occurs
     */
    public List<String> getImages() throws IOException {
        if (uploadDir == null) {
            throw new IllegalStateException("uploadDir is not set");
        }

        List<String> imageNames = new ArrayList<>();
        Path path = Paths.get(uploadDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.png")) { // Search all files within uploadDir with .png extension
            for (Path entry : stream) {
                imageNames.add(entry.getFileName().toString());
            }
        }

        return imageNames;
    }

    /**
     * Deletes an image from the upload directory
     * @param filename the name of the image file
     * @return true if the image was deleted, false otherwise
     * @throws IOException if an I/O error occurs
     */
    public boolean deleteImage(String filename) throws IOException {
        if (uploadDir == null) {
            throw new IllegalStateException("uploadDir is not set");
        }

        Path path = Paths.get(uploadDir).resolve(filename);

        return Files.deleteIfExists(path);
    }
}
