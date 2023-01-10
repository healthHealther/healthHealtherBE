package com.health.healther.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE SPACE SET SAPCE.DELETED_AT = CURRENT_TIMESTAMP WHERE SPACE.SAPCE_ID = ?")
@Getter
public class SpaceTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPACE_TIME_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Column(name = "OPEN_TIME")
	private int openTime;

	@Column(name = "CLOSE_TIME")
	private int closeTime;
}
