package com.electronicsstore.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SingInController {

    @GetMapping("/sign-in")
    public String SignIn(){
        return "sign-in";
    }

    @GetMapping("/register")
    public String Register(){
        return "register";
    }
}
