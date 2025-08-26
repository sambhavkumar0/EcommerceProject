package com.cts.ecommerce.controller;

import com.cts.ecommerce.model.User;
import com.cts.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {

        boolean hasError = false;

        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("usernameError", "Username already taken");
            hasError = true;
        }

        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("emailError", "Email already in use");
            hasError = true;
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            model.addAttribute("roleError", "Please select a role");
            hasError = true;
        }

        if (hasError) {
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/login?success";
    }
}
