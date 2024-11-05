package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.imageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller for handling image uploads and retrievals
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    @Autowired
    private imageService imageService;

    /**
     * Upload an image
     * @param file The image file
     * @param customName The custom name for the image
     * @return A response entity with the result of the upload
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "customName", required = false) String customName) {
        try {
            String filePath = imageService.saveImage(file, customName);
            return ResponseEntity.ok("Image uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    /**
     * Get an image
     * @param filename The name of the image file
     * @return The image file
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }
}