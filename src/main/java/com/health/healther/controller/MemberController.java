package com.health.healther.controller;

import static com.health.healther.constant.MemberStatus.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.domain.model.Member;
import com.health.healther.dto.member.MemberLoginResponseDto;
import com.health.healther.dto.member.MemberSearchResponseDto;
import com.health.healther.dto.member.MemberSignUpRequestDto;
import com.health.healther.dto.member.MemberUpdateRequestDto;
import com.health.healther.service.MemberService;
import com.health.healther.service.OauthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final OauthService oauthService;

	@PostMapping("/login/callback/{provider}")
	public ResponseEntity<?> oauthLogin(
		@PathVariable String provider,
		@RequestParam String code
	) {
		Member member = oauthService.getOauth(provider, code);
		if (member.getMemberStatus() == NEED_DATA) {
			return new ResponseEntity<>(oauthService.needDataResult(member), HttpStatus.MOVED_PERMANENTLY);
		} else {
			return ResponseEntity.ok(oauthService.loginResult(member));
		}
	}

	@PostMapping("/signUp")
	public ResponseEntity<MemberLoginResponseDto> signUp(
		@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto
	) {
		MemberLoginResponseDto response = oauthService.signUpAndCreateJwtAuth(memberSignUpRequestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity updateMember(
		@RequestBody @Valid MemberUpdateRequestDto memberUpdateRequestDto
	) {
		memberService.updateMember(memberUpdateRequestDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<MemberSearchResponseDto> searchMember(
	) {
		return ResponseEntity.ok(MemberSearchResponseDto.from(memberService.findUserFromToken()));
	}

	@DeleteMapping
	public ResponseEntity<Void> withdrawMember(
	) {
		memberService.deleteMember();
		return ResponseEntity.ok().build();
	}
}
