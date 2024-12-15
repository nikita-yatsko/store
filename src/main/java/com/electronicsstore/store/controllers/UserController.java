package com.electronicsstore.store.controllers;

import com.electronicsstore.store.models.User;
import com.electronicsstore.store.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        // Проверка на существование пользователя
        if (userRepository.findByUsername(user.getUsername()) != null) {
            logger.info("Пользователя с таким именем не существует");
            return "User with this username already exists!";
        }

        // Сохраняем пароль без шифрования
        userRepository.save(user);
        logger.info("Пользователь успешно добавлен");
        return "User added successfully!";
    }
}