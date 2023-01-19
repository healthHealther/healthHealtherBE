package com.health.healther.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ConvenienceType {
	SHOWER("SHOWER"),
	PARKING("PARKING"),
	DRESSING_ROOM("DRESSING_ROOM"),
	MIRROR("MIRROR"),
	WIFI("WIFI"),
	FOOD("FOOD"),
	BRING_FOOD("BRING_FOOD");

	@Getter
	private final String value;

	ConvenienceType(String value) {
		this.value = value;
	}

	@JsonCreator
	public static ConvenienceType from(String value) {
		for (ConvenienceType status : ConvenienceType.values()) {
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
