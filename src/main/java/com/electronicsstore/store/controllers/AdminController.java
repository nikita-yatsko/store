package com.electronicsstore.store.controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class AdminController {

    private final String UPLOAD_DIR = "images/";

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/main-admin") // админская сторона главной страницы
    public String mainAdmin(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "main-admin";
    }


    @GetMapping("/main-admin/about-product/{id}")
    public String aboutProduct(@PathVariable(value = "id") long id, Model model){
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> products = new ArrayList<>();
        product.ifPresent(products::add);
        model.addAttribute("product", product);
        return "about-product";
    }

}
