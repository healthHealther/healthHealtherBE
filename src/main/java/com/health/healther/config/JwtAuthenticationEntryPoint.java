package com.health.healther.config;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.healther.exception.member.MemberErrorCode;
import com.health.healther.exception.member.SendErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		Object errorObject = request.getAttribute("MemberCustomException");
		if (errorObject != null) {
			sendError(response, MemberErrorCode.UNAUTHORIZED_USER);
		}
		sendError(response, MemberErrorCode.FORBIDDEN_USER);
	}

	private void sendError(HttpServletResponse response, MemberErrorCode errorCode) throws IOException {
		response.setStatus(errorCode.getHttpStatus().value());
		response.setContentType("application/json,charset=utf-8");
		try (OutputStream os = response.getOutputStream()) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(os,
				new SendErrorResponse(errorCode.getHttpStatus().value(), errorCode.name(), errorCode.getDescription()));
			os.flush();
		}
	}
}
