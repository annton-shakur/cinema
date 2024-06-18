package com.example.cinema.controller.web;

import com.example.cinema.dto.user.UserLoginRequestDto;
import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.security.AuthenticationService;
import com.example.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthWebController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        model.addAttribute("userRegistrationRequestDto", new UserRegistrationRequestDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(final Model model,
                               @ModelAttribute final UserRegistrationRequestDto requestDto
    ) {
        try {
            userService.register(requestDto);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginRequestDto", new UserLoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginRequestDto requestDto, Model model) {
        try {
            authenticationService.authenticate(requestDto);
            return "redirect:/movies";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
