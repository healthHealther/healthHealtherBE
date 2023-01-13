package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
