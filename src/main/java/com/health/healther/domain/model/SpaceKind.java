package com.health.healther.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.health.healther.constant.SpaceType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE SPACE_KIND SET SPACE_KIND.DELETED_AT = CURRENT_TIMESTAMP WHERE SPACE_KIND.SPACE_KIND_ID = ?")
@Getter
public class SpaceKind extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPACE_KIND_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Column(name = "TYPE_NAME")
	@Enumerated(EnumType.STRING)
	private SpaceType spaceType;

	private SpaceKind(
			Space space,
			SpaceType spaceType
	) {
		this.space = space;
		this.spaceType = spaceType;
	}

	public static SpaceKind of(
			Space space,
			SpaceType spaceType
	) {
		return new SpaceKind(
				space,
				spaceType
		);
	}
}
