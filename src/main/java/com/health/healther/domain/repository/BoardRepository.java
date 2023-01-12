package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
