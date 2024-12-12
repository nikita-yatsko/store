package com.electronicsstore.store.controllers;


import com.electronicsstore.store.models.User;
import com.electronicsstore.store.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepository; // Репозиторий для сохранения пользователей


    // Обрабатываем POST-запрос с формы регистрации
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("confirm-password") String confirmPassword,
                               Model model) {

        // Проверяем, что пароли совпадают
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают!");
            return "register"; // Возвращаемся на страницу регистрации с ошибкой
        }

        // Проверяем, что пользователь с таким именем не существует
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Пользователь с таким именем уже существует!");
            return "register";
        }

        // Сохраняем пароль в открытом виде
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Сохраняем пароль без шифрования
        newUser.setRoles("USER"); // Роль по умолчанию

        // Сохраняем пользователя в базе данных
        userRepository.save(newUser);

        return "redirect:/login"; // Перенаправляем на страницу входа после успешной регистрации
    }
}
