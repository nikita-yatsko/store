package com.electronicsstore.store.controllers;


import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        model.addAttribute("buttonText", "Удалить");
        model.addAttribute("formActionPrefix", "/main-admin/remove/");

        // передает параметры кнопкам аккаунт/корзина и вход
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser");
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "main-admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
