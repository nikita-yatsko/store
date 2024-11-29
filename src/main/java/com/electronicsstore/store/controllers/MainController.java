package com.electronicsstore.store.controllers;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * Контроллер для управления продуктами в интернет-магазине.
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final ProductRepository productRepository;

    @Autowired
    public MainController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Отображает главную страницу с перечнем всех продуктов.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления для главной страницы
     */
    @GetMapping("/")
    public String main(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        logger.info("Отображение главной страницы с {} продуктами.", ((Collection<?>) products).size());
        return "main";
    }

    /**
     * Отображает страницу о продукте по его ID.
     *
     * @param id    идентификатор продукта
     * @param model модель для передачи данных в представление
     * @return имя представления для страницы о продукте или перенаправление на главную страницу, если продукт не найден
     */
    @GetMapping("/about-product/{id}")
    public String aboutProduct(@PathVariable(value = "id") long id, Model model) {
        return productRepository.findById(id)
                .map(product -> {
                    model.addAttribute("product", product); // Добавляем продукт в модель
                    logger.info("Продукт с ID {} найден и добавлен в модель.", id);
                    return "about-product";
                })
                .orElseGet(() -> {
                    logger.warn("Продукт с ID {} не найден. Перенаправление на главную страницу.", id);
                    return "redirect:/"; // Перенаправляем на главную страницу, если продукт не найден
                });
    }

    /**
     * Получает изображение продукта по его ID.
     *
     * @param id идентификатор продукта
     * @return изображение продукта в виде байтового массива или NOT FOUND, если продукт не найден
     */
    @GetMapping("/product/image/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductImage(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    if (product.getImage() != null) {
                        logger.info("Изображение продукта с ID {} успешно получено.", id);
                        return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(product.getImage());
                    }
                    logger.warn("Изображение для продукта с ID {} отсутствует.", id);
                    return ResponseEntity.notFound().build();
                })
                .orElseGet(() -> {
                    logger.warn("Продукт с ID {} не найден при запросе изображения.", id);
                    return ResponseEntity.notFound().build();
                });
    }
}