package com.health.healther.config;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.health.healther.dto.member.Token;
import com.health.healther.exception.member.UnauthorizedMemberException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	@Value("${jwt.access-token.expire-length}")
	private long accessTokenValidityInMilliseconds;

	@Value("${jwt.refresh-token.expire-length}")
	private long refreshTokenValidityInMilliseconds;

	@Value("${jwt.token.secret-key}")
	private String secretKey;

	public Token createAccessToken(String payload) {
		String token = createToken(payload, accessTokenValidityInMilliseconds);
		return new Token(token, accessTokenValidityInMilliseconds);
	}

	public Token createRefreshToken() {
		String token = createToken(UUID.randomUUID().toString(), refreshTokenValidityInMilliseconds);
		return new Token(token, refreshTokenValidityInMilliseconds);
	}

	private String createToken(String payload, long expiredLength) {
		Claims claims = Jwts.claims().setSubject(payload);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + expiredLength))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String getPayload(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		} catch (ExpiredJwtException e) {
			return e.getClaims().getSubject();
		} catch (JwtException e) {
			throw new UnauthorizedMemberException("로그인이 필요합니다.");
		}
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claimsJws.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.warn("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
}
