package com.example.boardapp.controller;

import com.example.boardapp.domain.Board;
import com.example.boardapp.domain.Comment;
import com.example.boardapp.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String boardList(Model model,
                            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page-1, size);
        model.addAttribute("boards", boardService.findAllBoard(pageable));
        model.addAttribute("currentPage", page);
        return "list";
    }

    @GetMapping("/writeform")
    public String showWriteForm(Model model) {
        model.addAttribute("board", new Board());
        return "writeform";
    }

    @PostMapping("/write")
    public String writeBoard(@ModelAttribute Board board) {
        boardService.saveBoard(board);
        return "redirect:/list";
    }

    @GetMapping("/updateform")
    public String showUpdateForm(Model model,
                                 @RequestParam(name = "id") Long id) {
        model.addAttribute("board", boardService.findBoardById(id));
        model.addAttribute("error", false);
        return "updateform";
    }

    @PostMapping("/update")
    public String updateBoard(Model model,
                              @ModelAttribute Board board,
                              @RequestParam(name = "password") String password) {
        Long id = board.getId();
        Board board2 = boardService.findBoardById(id);
        if (board2.getPassword().equals(password)) {
            boardService.updateBoard(board);
            return "redirect:/view?id=" + id;
        }
        model.addAttribute("error", true);
        return "updateform";
    }

    @GetMapping("/deleteform")
    public String showDeleteForm(Model model,
                                 @RequestParam(name = "id") Long id) {
        model.addAttribute("board", boardService.findBoardById(id));
        model.addAttribute("error", false);
        return "deleteform";
    }

    @PostMapping("/delete")
    public String deleteBoard(Model model,
                              @ModelAttribute Board board,
                              @RequestParam(name = "password") String password) {
        Board board2 = boardService.findBoardById(board.getId());
        if (board2.getPassword().equals(password)) {
            boardService.deleteBoard(board.getId());
            return "redirect:/list";
        }
        model.addAttribute("error", true);
        return "deleteform";
    }

    @GetMapping("/view")
    public String viewBoard(Model model,
                            @RequestParam(name = "id") Long id) {
        List<Comment> comments = boardService.findCommentsByBoardId(id);
        Comment newComment = new Comment();
        newComment.setBoardId(id);
        model.addAttribute("comment", newComment);
        model.addAttribute("board", boardService.findBoardById(id));
        model.addAttribute("comments", comments);
        return "view";
    }
}
