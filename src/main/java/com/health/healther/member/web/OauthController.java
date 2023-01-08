package com.health.healther.member.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.member.domain.LoginResponse;
import com.health.healther.member.service.OauthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {
	private final OauthService oauthService;

	@GetMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code) {
		return ResponseEntity.ok().body(oauthService.createJwtAuth(provider, code));
	}
}
