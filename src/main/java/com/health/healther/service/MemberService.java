package com.health.healther.service;

import static com.health.healther.exception.member.MemberErrorCode.*;

import org.springframework.stereotype.Service;

import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.exception.member.MemberCustomException;
import com.health.healther.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member findById(Long id) {
		return memberRepository.findById(id).orElseThrow(() -> new MemberCustomException(NOT_FOUND_MEMBER));
	}

	public Member findUserFromToken() {
		return memberRepository.findById(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new MemberCustomException(NOT_FOUND_MEMBER));
	}
}
