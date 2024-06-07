package com.BACKTP6.BACKTP6.controllers;

import com.BACKTP6.BACKTP6.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;


    @PostMapping("/uploads")
    public ResponseEntity<String> uploadImages(
            @RequestParam(value = "uploads", required = true) MultipartFile[] files) {
        try {

            return imageService.uploadImages(files);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/deleteImg")
    public ResponseEntity<String> deleteById(
            @RequestParam(value = "publicId", required = true) String publicId,
            @RequestParam(value = "uuid", required = true) String uuidString) {
        try {
            UUID uuid = UUID.fromString(uuidString);
            return imageService.deleteImage(publicId, uuid);
        } catch (IllegalArgumentException e) {
            // UUID.fromString lanzará una IllegalArgumentException si la cadena no es un UUID válido
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid UUID format");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred");
        }
    }
    @GetMapping("/getImages")
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        try {
            return  imageService.getAllImages();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };



}
