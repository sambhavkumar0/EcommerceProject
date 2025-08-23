package com.cts.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Handles http://localhost:8080/ or /home
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // renders home.html
    }
}
