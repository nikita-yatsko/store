package com.electronicsstore.store.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductAddController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/main-admin/product-add")
    public String productAdd(Model model) {
        List<String> types = Arrays.asList("Ноутбук");
        model.addAttribute("types", types);

        List<String> screens = Arrays.asList("Серсорный", "Несенсорный");
        model.addAttribute("screens", screens);

        List<String> screen_resolutions = Arrays.asList("1600x900", "1920x1080", "1920x1200", "2560x1440", "2560x1600", "2880x1800");
        model.addAttribute("screen_resolutions", screen_resolutions);

        List<String> screen_technologys = Arrays.asList("IPS", "TN+Film", "OLED");
        model.addAttribute("screen_technologys", screen_technologys);

        List<String> ram_capacitys = Arrays.asList("8", "16", "32", "64");
        model.addAttribute("ram_capacitys", ram_capacitys);

        List<String> hddCapacitys = Arrays.asList("нет", "256", "512", "1024");
        model.addAttribute("hddCapacitys", hddCapacitys);

        List<String> sddCapacitys = Arrays.asList("нет", "256", "512", "1024");
        model.addAttribute("sddCapacitys", sddCapacitys);

        List<String> operationSystems = Arrays.asList("Linux", "Windows 10", "Windows 10 Pro", "Windows 11", "Windows 11 Pro", "без OC", "Mac OS");
        model.addAttribute("operationSystems", operationSystems);
        return "product-add";
    }

    @PostMapping("/main-admin/product-add")
    public String productAddToDb(
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


        // Создание и сохранение продукта
        Product product = new Product(type, name_model, description, processorModel, videoCardModel,
                color, operationSystem, screen_resolution, ram_capacity, hddCapacity, sddCapacity,
                batteryCapacity, "---", screen_technology, screen, price,
                launchDate, screenDiagonal, grade);
        productRepository.save(product);

        return "redirect:/main-admin";
    }

}




