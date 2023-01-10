package com.health.healther.member.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.envers.AuditOverride;

import com.health.healther.member.domain.type.LoginType;
import com.health.healther.member.domain.type.MemberStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String nickName;

	private String phone;

	@Enumerated(EnumType.STRING)
	private MemberStatus memberStatus;

	private String oauthId;

	@Enumerated(EnumType.STRING)
	private LoginType loginType;

	private boolean isDeleted;
	// @OneToMany
	// @JoinColumn
	// private HomeGym homeGym;
	// @OneToMany
	// @JoinColumn
	// private Reservation reservation;
	// @OneToMany
	// @JoinColumn
	// private Review review;
	// @OneToMany
	// @JoinColumn
	// private Board board;
	// @OneToMany
	// @JoinColumn
	// private BoardLike boardLike;
	// @OneToMany
	// @JoinColumn
	// private Coupon coupon;

	@Builder
	public Member(String name, String nickName, String phone, MemberStatus memberStatus, String oauthId,
		LoginType loginType) {
		this.name = name;
		this.nickName = nickName;
		this.phone = phone;
		this.memberStatus = memberStatus;
		this.oauthId = oauthId;
		this.loginType = loginType;
	}

	public void signUp(String name, String nickName, String phone) {
		this.name = name;
		this.nickName = nickName;
		this.phone = phone;
		this.memberStatus = MemberStatus.ACTIVE;
	}
}
