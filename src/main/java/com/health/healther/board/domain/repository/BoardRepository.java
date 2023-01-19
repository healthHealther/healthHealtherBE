package com.health.healther.board.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.board.domain.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}