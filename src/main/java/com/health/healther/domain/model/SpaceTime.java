package com.health.healther.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.health.healther.dto.space.CreateSpaceRequestDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE SPACE_TIME SET SPACE_TIME.DELETED_AT = CURRENT_TIMESTAMP WHERE SPACE_TIME.SPACE_TIME_ID = ?")
@Getter
public class SpaceTime extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPACE_TIME_ID")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Column(name = "OPEN_TIME")
	private int openTime;

	@Column(name = "CLOSE_TIME")
	private int closeTime;

	private SpaceTime(
			Space space,
			int openTime,
			int closeTime
	) {
		this.space = space;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	public static SpaceTime of(
			Space space,
			CreateSpaceRequestDto createSpaceRequestDto
	) {
		return new SpaceTime(
				space,
				createSpaceRequestDto.getOpenTime(),
				createSpaceRequestDto.getCloseTime()
		);
	}
}
