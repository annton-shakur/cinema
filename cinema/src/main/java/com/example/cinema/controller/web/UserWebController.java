package com.example.cinema.controller.web;

import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.model.User;
import com.example.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserWebController {
    private final UserService userService;

    @GetMapping
    public String getUserProfile(final Authentication authentication, final Model model) {
        User userFromAuth = (User) authentication.getPrincipal();
        UserResponseDto user = userService.findById(userFromAuth.getId());
        model.addAttribute("user", user);
        return "profile";
    }
}
