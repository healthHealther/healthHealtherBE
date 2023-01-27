package com.health.healther.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.dto.member.MemberUpdateRequestDto;
import com.health.healther.exception.member.NotFoundMemberException;
import com.health.healther.exception.member.NotMatchMemberException;
import com.health.healther.util.RedisUtil;
import com.health.healther.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final RedisUtil redisUtil;

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new NotFoundMemberException("회원 정보를 찾을 수 없습니다."));
	}

	public Member findUserFromToken() {
		return memberRepository.findById(SecurityUtil.getCurrentUserId())
			.orElseThrow(() -> new NotMatchMemberException("토큰 정보와 일치하는 회원 정보가 없습니다."));
	}

	@Transactional
	public void updateMember(MemberUpdateRequestDto memberUpdateRequestDto) {
		Member member = findUserFromToken();
		member.updateFromSignUpForm(
			memberUpdateRequestDto.getName(),
			memberUpdateRequestDto.getNickName(),
			memberUpdateRequestDto.getPhone()
		);
	}

	@Transactional
	public void deleteMember() {
		Member member = findUserFromToken();
		redisUtil.deleteData(String.valueOf(member.getId()));
		memberRepository.delete(member);
	}
}
