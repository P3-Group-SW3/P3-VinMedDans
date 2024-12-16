package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.ImageService;
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
import java.util.List;

/**
 * Controller for handling image uploads and retrievals
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    @Autowired
    private ImageService ImageService;

    /**
     * Upload an image
     *
     * @param file       The image file
     * @param customName The custom name for the image
     * @return A response entity with the result of the upload
     */
    @PostMapping("/admin/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "customName", required = false) String customName) {
        try {
            String filePath = ImageService.saveImage(file, customName);
            return ResponseEntity.ok("Image uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    /**
     * Get an image
     *
     * @param filename The name of the image file
     * @return The image file
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) { // If request contains illegal file chars
                throw new IllegalArgumentException("Invalid filename");
            }
            Path path = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Respond with image in JPEG format, JPEG has smaller size = faster download for end user
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Get a list of images
     *
     * @return A response entity with the list of images
     */
    @GetMapping("/getImageList")
    public ResponseEntity<List<String>> getImages() {
        try {
            List<String> imageList = ImageService.getImages();
            return ResponseEntity.ok(imageList);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
