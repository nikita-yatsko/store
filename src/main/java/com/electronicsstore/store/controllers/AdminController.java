package com.electronicsstore.store.controllers;


import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.models.User;
import com.electronicsstore.store.repo.ProductRepository;
import com.electronicsstore.store.repo.UserRepository;
import com.electronicsstore.store.services.ProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final ProductRepository productRepository;
    private final ProductDataService productDataService;
    private final UserRepository userRepository;


    @Autowired
    public AdminController(ProductRepository productRepository, ProductDataService productDataService, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.productDataService = productDataService;
        this.userRepository = userRepository;
    }

    /**
     * Принимает данные для добавления товара в корзину.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления для главной страницы администатора
     */
    @GetMapping("/main-admin")
    public String mainAdmin(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        model.addAttribute("pathToLink", "/main-admin/edit-product/");
        model.addAttribute("buttonText", "Удалить");
        model.addAttribute("formActionPrefix", "/main-admin/remove/");

        return "main-admin";
    }

    /**
     * Возвращает страницу администатора для редактирования товара.
     *
     * @param id идентификатор продукта
     * @param model модель для передачи данных в представление
     * @return имя представления для страницы редактирования
     */
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

    @GetMapping("/main-admin/user-window")
    public String adminUserWindow(Model model, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        Iterable<User> users = userRepository.findAll();

        model.addAttribute("adminName", user.getUsername());
        model.addAttribute("adminPassword", user.getPassword());
        model.addAttribute("users", users);

        return "admin-accaunt";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("id") Long id){
        userRepository.deleteById(id);

        return "redirect:/main-admin/user-window";
    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam("id") Long id, @RequestParam("role") String role, Principal principal){
        return userRepository.findById(id)
                .map(user -> {
                    user.setRoles(role);
                    userRepository.save(user);
                    logger.info("Была изменена роль пользователя с именем {} на роль {}", user.getUsername(), role);

                    if(user.getId() == userRepository.findByUsername(principal.getName()).getId())
                        return "redirect:/";

                    return "redirect:/main-admin/user-window";
                })
                .orElseGet(() -> {
                    return "redirect:/main-admin"; // Перенаправляем на главную страницу, если продукт не найден
                });
    }
}
