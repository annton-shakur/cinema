package com.example.cinema.controller.web;

import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.model.User;
import com.example.cinema.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentWebController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public String addComment(@ModelAttribute final CommentCreateDto commentDto,
                             final Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        commentService.saveComment(commentDto, user.getId());
        return "redirect:/movies/" + commentDto.getMovieId();
    }
}
