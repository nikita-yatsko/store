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

        return "about-product-admin"; // Возвращаем имя шаблона
    }


    /*@PostMapping("/main-admin/about-product-admiin/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @RequestParam String type,
            @RequestParam String name_model,
            @RequestParam int launchDate,
            @RequestParam String processorModel,
            @RequestParam String videoCardModel,
            @RequestParam double screenDiagonal,
            @RequestParam String screen_resolution,
            @RequestParam String screen_technology,
            @RequestParam String screen,
            @RequestParam int ram_capacity,
            @RequestParam String hddCapacity,
            @RequestParam String sddCapacity,
            @RequestParam int batteryCapacity,
            @RequestParam String color,
            @RequestParam String operationSystem,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam double grade,
            Model model) throws IOException {

        // Найти продукт по идентификатору
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // Обновление полей продукта
        product.setType(type);
        product.setNameModel(name_model);
        product.setLaunchDate(launchDate);
        product.setProcessorModel(processorModel);
        product.setVideoCardModel(videoCardModel);
        product.setScreenDiagonal(screenDiagonal);
        product.setScreenResolution(screen_resolution);
        product.setScreenTechnology(screen_technology);
        product.setScreen(screen);
        product.setRamCapacity(ram_capacity);
        product.setHddCapacity(hddCapacity);
        product.setSsdCapacity(sddCapacity);
        product.setBatteryCapacity(batteryCapacity);
        product.setColor(color);
        product.setOperationSystem(operationSystem);
        product.setDescription(description);
        product.setPrice(price);
        product.setGrade(grade);


        // Сохранение обновленного продукта
        productRepository.save(product);

        return "redirect:/main-admin";
    }*/


    @PostMapping("/main-admin/{id}/remove")
    public String productDelete(@PathVariable Long id, Model model){
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);

        return "redirect:/main-admin";
    }


}




