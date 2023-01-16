package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.member.MemberSearchResponse;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PutMapping("/member/{memberId}")
	public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @RequestBody @Valid SignUpForm form) {
		memberService.updateMember(memberId, form);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/member/{memberId}")
	public ResponseEntity<MemberSearchResponse> searchMember(@PathVariable Long memberId) {
		return ResponseEntity.ok(MemberSearchResponse.from(memberService.searchMember(memberId)));
	}

	@DeleteMapping("/member/{memberId}")
	public ResponseEntity<Void> withDrawMember(@PathVariable Long memberId) {
		memberService.deleteMember(memberId);
		return ResponseEntity.ok().build();
	}
}
