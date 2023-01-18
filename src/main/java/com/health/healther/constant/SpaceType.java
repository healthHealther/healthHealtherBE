package com.health.healther.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum SpaceType {
	GX("GX"),
	PILATES("PILATES"),
	YOGA("YOGA");

	@Getter
	private final String value;

	SpaceType(String value) {
		this.value = value;
	}

	@JsonCreator
	public static SpaceType from(String value) {
		for (SpaceType status : SpaceType.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
