package com.health.healther.domain.repository;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByBoard(Board board, PageRequest pageRequest);
}
