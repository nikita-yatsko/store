package com.electronicsstore.store.controllers;


import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import com.electronicsstore.store.services.ProductDataService;
import com.electronicsstore.store.services.ProductDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
public class AdminController {

    @Autowired
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private ProductDataService productDataService = new ProductDataServiceImpl();

    @GetMapping("/main-admin")
    public String mainAdmin(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        model.addAttribute("pathToLink", "/main-admin/edit-product/");
        model.addAttribute("buttonText", "Удалить");
        model.addAttribute("formActionPrefix", "/main-admin/remove/");

        return "main-admin";
    }

    @GetMapping("/main-admin/edit-product/{id}")
    public String editProductAdmin(@PathVariable Long id, Model model){
        return productRepository.findById(id)
                .map(product -> {
                    model.addAttribute("product", product); // Добавляем продукт в модель
                    productDataService.populateProductData(model);
                    return "/edit-product";
                })
                .orElseGet(() -> {
                    return "redirect:/main-admin"; // Перенаправляем на главную страницу, если продукт не найден
                });
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable Long id, Product newProduct){
        Optional<Product> productOptional = productRepository.findById(id);
        Product productOld = productOptional.get();
        newProduct.setImage(productOld.getImage());

        productRepository.save(newProduct);
        productRepository.deleteById(id);

        //logger.info("Был удален продукт с ID {} и вместо него добавлен продукт с ID", id, newProduct.getProduct_id());

        return "redirect:/main-admin";
    }
}
