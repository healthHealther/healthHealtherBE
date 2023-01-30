package com.health.healther.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTransformUtil {
	public static final String AUTHORIZATION = "Authorization";
	public static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";
	public static String BEARER_TYPE = "Bearer";

	public static String resolveAccessTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (!ObjectUtils.isEmpty(token) && token.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
			return token.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}

	public static String resolveRefreshTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(REFRESH_TOKEN_HEADER);
		if (!ObjectUtils.isEmpty(token)) {
			return token;
		}
		return null;
	}
}
