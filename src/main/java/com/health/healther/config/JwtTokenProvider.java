package com.health.healther.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

	public String createAccessToken(String payload) {
		return createToken(payload, accessTokenValidityInMilliseconds);
	}

	public String createRefreshToken() {
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		String generatedString = new String(array, StandardCharsets.UTF_8);
		return createToken(generatedString, refreshTokenValidityInMilliseconds);
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
			throw new RuntimeException("유효하지 않은 토큰 입니다");
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
