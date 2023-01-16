package com.health.healther.domain.model;

import javax.persistence.*;

import com.health.healther.review.domain.model.Review;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.health.healther.dto.space.CreateSpaceRequestDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	@JoinColumn(name = "reviews")
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

	private Space(
			Member member,
			String title,
			String content,
			String address,
			String addressDetail,
			String notice,
			String rule,
			int price
	) {
		this.member = member;
		this.title = title;
		this.content = content;
		this.address = address;
		this.addressDetail = addressDetail;
		this.notice = notice;
		this.rule = rule;
		this.price = price;
	}

	public static Space of(
			CreateSpaceRequestDto createSpaceRequestDto
	) {
		return new Space(
				null,
				createSpaceRequestDto.getTitle(),
				createSpaceRequestDto.getContent(),
				createSpaceRequestDto.getAddress(),
				createSpaceRequestDto.getAddressDetail(),
				createSpaceRequestDto.getNotice(),
				createSpaceRequestDto.getRule(),
				createSpaceRequestDto.getPrice()
		);
	}
}
