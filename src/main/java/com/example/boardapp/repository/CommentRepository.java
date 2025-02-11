package com.example.boardapp.repository;

import com.example.boardapp.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByBoardIdOrderByCreatedAtDesc(Long boardId);
}
