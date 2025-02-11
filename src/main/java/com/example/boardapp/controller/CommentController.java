package com.example.boardapp.controller;

import com.example.boardapp.domain.Comment;
import com.example.boardapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/add")
    public String addComment(@ModelAttribute Comment comment) {
        commentService.addComment(comment);
        return "redirect:/view?id="+comment.getBoardId();
    }
}
