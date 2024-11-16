package com.electronicsstore.store.controllers;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/product/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent() && productOptional.get().getImage() != null) {
            byte[] imageBytes = productOptional.get().getImage();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Убедитесь, что это правильный тип
                    .body(imageBytes);
        }
        return ResponseEntity.notFound().build();
    }


    /*@GetMapping("/about-product/{id}")
    public String aboutProduct(@PathVariable(value = "id") long id, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "about-product";
        } else {
            // Handle product not found case, e.g., redirect to main or show error page
            return "redirect:/"; // Redirect to main page or handle error appropriately
        }
    }*/


}
