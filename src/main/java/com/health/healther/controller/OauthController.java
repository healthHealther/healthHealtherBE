package com.health.healther.controller;

import static com.health.healther.constant.MemberStatus.*;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.member.LoginResponseDto;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.service.OauthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {
	private final OauthService oauthService;

	@GetMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<LoginResponseDto> login(@PathVariable String provider, @RequestParam String code) {
		LoginResponseDto response = oauthService.createJwtAuth(provider, code);
		return getResponseEntity(provider, code, response);
	}

	@PostMapping("/login/oauth2/signUp")
	public ResponseEntity<LoginResponseDto> signUp(@RequestParam String oauthId, @RequestBody SignUpForm form) {
		return ResponseEntity.ok(oauthService.signUpAndCreateJwtAuth(oauthId, form));
	}

	private ResponseEntity<LoginResponseDto> getResponseEntity(String provider, String code,
		LoginResponseDto response) {
		if (response.getMemberStatus().equals(NEED_DATA)) {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
				URI.create("http://localhost:8080/login/oauth2/signUp?oauthId=" + response.getOauthId()));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
		return ResponseEntity.ok().body(oauthService.createJwtAuth(provider, code));
	}
}
