package com.health.healther.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.health.healther.service.AuthService;
import com.health.healther.util.UserAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthService authService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = resolveTokenFromRequest(request);
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			Long id = authService.findMemberByToken(token);
			UserAuthentication authentication = new UserAuthentication(id);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String resolveTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);
		if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
			return token.substring(TOKEN_PREFIX.length());
		}
		return null;
	}
}
