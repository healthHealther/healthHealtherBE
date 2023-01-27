package com.health.healther.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.config.JwtTokenProvider;
import com.health.healther.domain.model.Member;
import com.health.healther.dto.member.AccessTokenResponseDto;
import com.health.healther.dto.member.RefreshTokenRequestDto;
import com.health.healther.dto.member.Token;
import com.health.healther.exception.member.InvalidTokenException;
import com.health.healther.exception.member.UnauthorizedAccessTokenException;
import com.health.healther.exception.member.UnauthorizedRefreshTokenException;
import com.health.healther.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberService memberService;
	private final RedisUtil redisUtil;

	public Long findMemberByToken(String accessToken) {
		if (!accessToken.isEmpty()) {
			validateAccessToken(accessToken);
		}
		Long id = Long.parseLong(jwtTokenProvider.getPayload(accessToken));
		Member member = memberService.findById(id);
		return member.getId();
	}

	public AccessTokenResponseDto accessTokenByRefreshToken(
		String accessToken,
		RefreshTokenRequestDto refreshTokenRequest
	) {
		validateRefreshToken(refreshTokenRequest);
		String id = jwtTokenProvider.getPayload(accessToken);
		String data = redisUtil.getData(id);
		if (!data.equals(refreshTokenRequest.getRefreshToken())) {
			throw new InvalidTokenException("유효하지 않는 액세스 토큰입니다.");
		}
		Token newAccessToken = jwtTokenProvider.createAccessToken(id);
		return AccessTokenResponseDto
			.builder()
			.accessToken(newAccessToken.getValue())
			.accessTokenExpiredTime(newAccessToken.getExpiredTime())
			.build();
	}

	@Transactional
	public String logout(String accessToken) {
		String id = jwtTokenProvider.getPayload(accessToken);
		redisUtil.deleteData(id);
		return "로그아웃이 완료 되었습니다.";
	}

	private void validateAccessToken(String accessToken) {
		if (!jwtTokenProvider.validateToken(accessToken)) {
			throw new UnauthorizedAccessTokenException("인가되지 않은 access 토큰입니다.");
		}
	}

	private void validateRefreshToken(RefreshTokenRequestDto refreshTokenRequest) {
		if (!jwtTokenProvider.validateToken(refreshTokenRequest.getRefreshToken())) {
			throw new UnauthorizedRefreshTokenException("인가되지 않은 refresh 토큰입니다.");
		}
	}
}
