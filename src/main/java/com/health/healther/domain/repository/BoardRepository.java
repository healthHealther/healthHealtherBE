package com.health.healther.domain.repository;

import com.health.healther.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitleAndMember(String title , Member member);
}
