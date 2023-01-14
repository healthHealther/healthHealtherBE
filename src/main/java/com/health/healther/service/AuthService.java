package com.health.healther.service;

import static com.health.healther.exception.member.MemberErrorCode.*;

import org.springframework.stereotype.Service;

import com.health.healther.config.JwtTokenProvider;
import com.health.healther.domain.model.Member;
import com.health.healther.exception.member.MemberCustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberService memberService;

	public Long findMemberByToken(String accessToken) {
		if (!accessToken.isEmpty()) {
			accessTokenCheck(accessToken);
		}
		Long id = Long.parseLong(jwtTokenProvider.getPayload(accessToken));
		Member member = memberService.findById(id);
		return member.getId();
	}

	public void accessTokenCheck(String accessToken) {
		if (!jwtTokenProvider.validateToken(accessToken)) {
			throw new MemberCustomException(UNAUTHORIZED_ACCESS_TOKEN);
		}
	}
}
