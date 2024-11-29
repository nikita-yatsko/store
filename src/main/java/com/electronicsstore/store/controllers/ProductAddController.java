package com.electronicsstore.store.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import com.electronicsstore.store.services.ProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductAddController {
    private final ProductRepository productRepository;
    private final ProductDataService productDataService;

    @Autowired
    public ProductAddController(ProductRepository pr, ProductDataService pds) {
        this.productRepository = pr;
        this.productDataService = pds;
    }

    @GetMapping("/main-admin/product-add")
    public String productAdd(Model model) {
        productDataService.populateProductData(model);
        return "product-add";
    }

    @PostMapping("/main-admin/product-add")
    public String productAddToDb(
            @ModelAttribute Product product,
            @RequestParam MultipartFile image_edit,
            Model model) throws IOException {

        byte[] imageBytes = image_edit.getBytes();
        product.setImage(imageBytes);

        productRepository.save(product);
        return "redirect:/main-admin";
    }

    @PostMapping("/main-admin/product-add1")
    public String productAdd(Product product){
        productRepository.save(product);
        return "redirect:/main-admin";
    }

}




