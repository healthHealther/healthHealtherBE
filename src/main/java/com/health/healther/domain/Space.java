package com.health.healther.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.health.healther.constant.SpaceType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE SPACE SET SAPCE.DELETED_AT = CURRENT_TIMESTAMP WHERE SPACE.SAPCE_ID = ?")
@Getter
public class Space {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPACE_ID")
	private Long id;

	// @ManyToOne
	// @JoinColumn(name = "MEMBER_ID")
	// private Member member;

	@Enumerated(EnumType.STRING)
	@Column(name = "SPACE_TYPE")
	private SpaceType spaceType;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "ADDRESS_DETAIL")
	private String addressDetail;

	@Column(name = "NOTICE")
	private String notice;

	@Column(name = "RULE")
	private String rule;

	@Column(name = "PRICE")
	private int price;
}
