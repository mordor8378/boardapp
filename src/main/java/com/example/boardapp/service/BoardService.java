package com.example.boardapp.service;

import com.example.boardapp.domain.Board;
import com.example.boardapp.domain.Comment;
import com.example.boardapp.repository.BoardRepository;
import com.example.boardapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Page<Board> findAllBoard(Pageable pageable) {
        Pageable pageable1 = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return boardRepository.findAll(pageable1);
    }

    public Board saveBoard(Board board) {
        if (board.getCreatedAt() == null) {
            board.setCreatedAt(LocalDateTime.now());
        }
        return boardRepository.save(board);
    }

    public Board updateBoard(Board board) {
        board.setUpdatedAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public List<Comment> findCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardIdOrderByCreatedAtDesc(boardId);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
