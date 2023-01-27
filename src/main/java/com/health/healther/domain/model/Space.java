package com.health.healther.domain.model;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.health.healther.dto.space.CreateSpaceRequestDto;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE SPACE SET SAPCE.DELETED_AT = CURRENT_TIMESTAMP WHERE SPACE.SAPCE_ID = ?")
@Getter
public class Space extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPACE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany(mappedBy = "space")
	private List<Review> reviews = new ArrayList<>();

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

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "space")
	private SpaceTime spaceTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "space")
	private Set<SpaceKind> spaceKinds = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "space")
	private Set<Convenience> conveniences = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "space")
	private List<Image> images;

	public static Space of(
			CreateSpaceRequestDto createSpaceRequestDto,
			Member member
	) {
		return Space.builder()
				.member(member)
				.title(createSpaceRequestDto.getTitle())
				.content(createSpaceRequestDto.getContent())
				.address(createSpaceRequestDto.getAddress())
				.addressDetail(createSpaceRequestDto.getAddressDetail())
				.notice(createSpaceRequestDto.getNotice())
				.rule(createSpaceRequestDto.getRule())
				.price(createSpaceRequestDto.getPrice())
				.build();
	}
}
