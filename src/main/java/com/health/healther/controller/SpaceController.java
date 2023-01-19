package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Long> createSpace(@RequestBody @Valid CreateSpaceRequestDto createSpaceRequestDto) {
		return new ResponseEntity(spaceService.createSpace(createSpaceRequestDto), HttpStatus.CREATED);
	}

	@GetMapping("/{spaceId}")
	public ResponseEntity getSpaceDetail(@PathVariable("spaceId") Long spaceId) {
		return ResponseEntity.ok().body(spaceService.getSpaceDetail(spaceId));
	}
}
