package com.vmd.vmdwebshop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestImageService {

    private ImageService imageService;
    private String uploadDir = "test-uploads";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        imageService = new ImageService();
        Field uploadDirField = ImageService.class.getDeclaredField("uploadDir");
        uploadDirField.setAccessible(true);
        uploadDirField.set(imageService, uploadDir);
        new File(uploadDir).mkdirs();
    }

    @AfterEach
    void tearDown() throws IOException {
        FileSystemUtils.deleteRecursively(new File(uploadDir));
    }

    @Test
    void saveImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test image content".getBytes());
        String customName = "custom_test_image";

        String savedPath = imageService.saveImage(file, customName);

        assertTrue(Files.exists(Paths.get(savedPath)));
        assertEquals(uploadDir + "\\" + customName + ".png", savedPath);
    }

    @Test
    void getImages() throws IOException {
        MockMultipartFile file1 = new MockMultipartFile("file", "test1.png", "image/png", "test image content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "test2.png", "image/png", "test image content".getBytes());

        imageService.saveImage(file1, "test1");
        imageService.saveImage(file2, "test2");

        List<String> images = imageService.getImages();

        assertEquals(2, images.size());
        assertTrue(images.contains("test1.png"));
        assertTrue(images.contains("test2.png"));
    }

    @Test
    void deleteImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test image content".getBytes());
        String customName = "custom_test_image";

        String savedPath = imageService.saveImage(file, customName);
        assertTrue(Files.exists(Paths.get(savedPath)));

        boolean isDeleted = imageService.deleteImage(customName + ".png");
        assertTrue(isDeleted);
        assertFalse(Files.exists(Paths.get(savedPath)));
    }

    @Test
    void saveImageThrowsIllegalStateExceptionWhenUploadDirNotSet() {
        imageService = new ImageService(); // uploadDir is not set
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test image content".getBytes());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.saveImage(file, "test");
        });

        assertEquals("uploadDir is not set", exception.getMessage());
    }

    @Test
    void getImagesThrowsIllegalStateExceptionWhenUploadDirNotSet() {
        imageService = new ImageService(); // uploadDir is not set

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.getImages();
        });

        assertEquals("uploadDir is not set", exception.getMessage());
    }

    @Test
    void deleteImageThrowsIllegalStateExceptionWhenUploadDirNotSet() {
        imageService = new ImageService(); // uploadDir is not set

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.deleteImage("test.png");
        });

        assertEquals("uploadDir is not set", exception.getMessage());
    }

    @Test
    void saveImageThrowsIllegalArgumentExceptionForInvalidCustomName() {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test image content".getBytes());
        String invalidCustomName = "invalid/../name";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.saveImage(file, invalidCustomName);
        });

        assertEquals("Invalid custom name", exception.getMessage());
    }
}
