package com.cts.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cts.ecommerce.model.User;
import com.cts.ecommerce.service.UserService;
import com.cts.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }

        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    
    @Autowired
    private ProductService productService;

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        User user = userService.loginUser(username, password);

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("products", productService.getAllProducts()); // show products after login
            return "profile";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }


    
    
    
}
