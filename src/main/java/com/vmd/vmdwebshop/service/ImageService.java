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

    public List<String> getImages() throws IOException {
        if (uploadDir == null) {
            throw new IllegalStateException("uploadDir is not set");
        }

        List<String> imageNames = new ArrayList<>();
        Path path = Paths.get(uploadDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.png")) {
            for (Path entry : stream) {
                imageNames.add(entry.getFileName().toString());
            }
        }

        return imageNames;
    }

    public boolean deleteImage(String filename) throws IOException {
        if (uploadDir == null) {
            throw new IllegalStateException("uploadDir is not set");
        }

        Path path = Paths.get(uploadDir).resolve(filename);

        return Files.deleteIfExists(path);
    }
}