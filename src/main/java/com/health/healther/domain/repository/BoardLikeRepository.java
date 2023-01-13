package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
}
