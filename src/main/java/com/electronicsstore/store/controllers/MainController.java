package com.electronicsstore.store.controllers;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/")
    public String main(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "main";
    }


    @GetMapping("/about-product/{id}")
    public String aboutProduct(@PathVariable(value = "id") long id, Model model){
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> products = new ArrayList<>();
        product.ifPresent(products::add);
        model.addAttribute("product", product.get());
        return "about-product";
    }


}
