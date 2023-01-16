package com.health.healther.exception.member;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberErrorResponse {
	private final int status;
	private final String error;
	private final String code;
	private final String description;
	private final String message;

	public static ResponseEntity<MemberErrorResponse> toResponseEntity(MemberCustomException e) {
		MemberErrorCode errorCode = e.getErrorCode();

		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(MemberErrorResponse.builder()
				.status(errorCode.getHttpStatus().value())
				.error(errorCode.getHttpStatus().name())
				.code(errorCode.name())
				.description(errorCode.getDescription())
				.message(e.getMessage())
				.build());
	}
}
