package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.member.MemberDto;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PutMapping("/users/{memberId}")
	public ResponseEntity<MemberDto> updateMember(@PathVariable Long memberId, @RequestBody @Valid SignUpForm form) {
		return ResponseEntity.ok(MemberDto.from(memberService.updateMember(memberId, form)));
	}
}
