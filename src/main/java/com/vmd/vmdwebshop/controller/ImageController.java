package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.service.imageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    private imageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file to the directory
            String filePath = imageService.saveImage(file);
            return ResponseEntity.ok("Image uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }
}