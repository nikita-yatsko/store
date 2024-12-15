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

/**
 * Контроллер для редактирование существующих товаров.
 */
@Controller
public class ProductEdit {

    private final ProductRepository productRepository;
    private final ProductDataService productDataService;

    @Autowired
    public ProductEdit(ProductRepository pr, ProductDataService pds) {
        this.productRepository = pr;
        this.productDataService = pds;
    }

    /**
     * Отображает страницу с подробной информацией о товаре.
     *
     * @param id идентификатор продукта
     * @param model модель для передачи данных в представление
     * @return имя представления для страницы педактирования товара.
     */
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

    /**
     * Принимате данные для удаления товара.
     *
     * @param id идентификатор продукта
     * @return имя представления для главной страницы администатора
     */
    @PostMapping("/main-admin/remove/{id}")
    public String productDelete(@PathVariable Long id){
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);

        return "redirect:/main-admin";
    }
}