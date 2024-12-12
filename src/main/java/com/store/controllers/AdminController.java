package com.store.controllers;


import com.store.models.Product;
import com.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/main-admin")
    public String mainAdmin(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "main-admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
