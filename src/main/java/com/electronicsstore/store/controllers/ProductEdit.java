package com.electronicsstore.store.controllers;


import java.util.Optional;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import com.electronicsstore.store.services.ProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ProductEdit {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDataService productDataService;

    @GetMapping("/main-admin/about-product-admin/{id}")
    public String aboutProductAdminToEdit(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get(); // Извлекаем сам объект Product
            model.addAttribute("product", product); // Добавляем в модель
            productDataService.populateProductData(model);
        } else {
            // Обработка случая, если продукт не найден
            model.addAttribute("errorMessage", "Продукт не найден"); // Добавление сообщения об ошибке
            return "404"; // Или редирект на страницу со списком продуктов
        }

        return "edit-product"; // Возвращаем имя шаблона
    }

    @PostMapping("/main-admin/remove/{id}")
    public String productDelete(@PathVariable Long id, Model model){
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);

        return "redirect:/main-admin";
    }


}




