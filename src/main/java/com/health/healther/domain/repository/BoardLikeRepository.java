package com.health.healther.domain.repository;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.BoardLike;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByMemberAndBoard(Member member, Board board);
}
