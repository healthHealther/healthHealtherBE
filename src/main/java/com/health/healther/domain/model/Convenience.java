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

import com.health.healther.constant.ConvenienceType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE CONVENIENCE SET CONVENIENCE.DELETED_AT = CURRENT_TIMESTAMP WHERE CONVENIENCE.CONVENIENCE_ID = ?")
@Getter
public class Convenience extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONVENIENCE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Enumerated(EnumType.STRING)
	@Column(name = "CONVENIENCE_TYPE")
	private ConvenienceType convenienceType;

	private Convenience(
			Space space,
			ConvenienceType convenienceType
	) {
		this.space = space;
		this.convenienceType = convenienceType;
	}

	public static Convenience of(
			Space space,
			ConvenienceType convenienceType
	) {
		return new Convenience(
				space,
				convenienceType
		);
	}
}
