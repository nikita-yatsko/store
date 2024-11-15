package com.electronicsstore.store.controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.electronicsstore.store.models.Image;
import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam MultipartFile file,
            Model model) throws IOException {

        Image image1;
        Image image2;
        Image image3;

        if (file.getSize() != 0) {
            image1 = toImageEntity(file);
            image1.setPreviewImage(true);
            Product product = new Product();
            product.addImageToProduct(image1);
        }




        // Создание и сохранение продукта
        Product product = new Product(type, name_model, description, processorModel, videoCardModel,
                color, operationSystem, screen_resolution, ram_capacity, hddCapacity, sddCapacity,
                batteryCapacity, "ddd", screen_technology, screen, price,
                launchDate, screenDiagonal, grade);

        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);

        return "redirect:/main-admin";
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());

        return image;
    }


}




