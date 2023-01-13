package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.space.CreateSpaceRequestDto;
import com.health.healther.service.SpaceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/space")
@RestController
public class SpaceController {
	private final SpaceService spaceService;

	@PostMapping
	public ResponseEntity createSpace(@RequestBody @Valid CreateSpaceRequestDto createSpaceRequestDto) {
		spaceService.createSpace(createSpaceRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
