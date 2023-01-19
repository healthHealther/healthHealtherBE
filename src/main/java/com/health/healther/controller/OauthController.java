package com.health.healther.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.member.LoginResponse;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.service.OauthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {
	@Value("${oauth.sign-up.redirect-url}")
	private String redirectURL;
	private final OauthService oauthService;

	@PostMapping("/login/callback/{provider}")
	public ResponseEntity<LoginResponse> oauthLogin(@PathVariable String provider, @RequestParam String code) {
		LoginResponse response = oauthService.getOauth(provider, code);
		return getResponseEntity(response);
	}

	@PostMapping("/login/oauth2/signUp")
	public ResponseEntity<LoginResponse> signUp(@RequestParam String oauthId, @RequestBody @Valid SignUpForm form) {
		LoginResponse response = oauthService.signUpAndCreateJwtAuth(oauthId, form);
		return  new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	private ResponseEntity<LoginResponse> getResponseEntity(LoginResponse response) {
		if (response.getTokenType() == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(redirectURL));
			return new ResponseEntity<>(response, headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			return ResponseEntity.ok(response);
		}
	}
}
