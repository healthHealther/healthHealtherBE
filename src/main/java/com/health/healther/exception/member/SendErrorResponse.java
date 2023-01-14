package com.health.healther.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendErrorResponse {
	private int status;
	private String code;
	private String message;
}
