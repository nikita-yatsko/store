package com.electronicsstore.store.controllers;

import java.util.HashMap;
import java.util.Map;

import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/main-admin/product-upload")
    public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Файл не выбран");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String filename = file.getOriginalFilename();
            System.out.println("Сохраняем файл: " + filename);
            file.transferTo(new java.io.File("images/" + filename));
            response.put("message", "Файл успешно загружен: " + filename);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Ошибка при загрузке файла");
            return ResponseEntity.status(500).body(response);
        }
    }
}


