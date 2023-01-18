package com.health.healther.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByOauthId(String oauthId);
}
