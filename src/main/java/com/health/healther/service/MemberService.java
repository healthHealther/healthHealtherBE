package com.health.healther.service;

import static com.health.healther.exception.member.MemberErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.exception.member.MemberCustomException;
import com.health.healther.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@Transactional
	public Member updateMember(Long memberId, SignUpForm form) {
		Member member = findUserFromToken();
		if (!member.getId().equals(memberId)) {
			throw new MemberCustomException(NOT_FOUND_MEMBER);
		}
		member.updateFromSignUpForm(form.getName(), form.getNickName(), form.getPhone());
		return member;
	}
}
