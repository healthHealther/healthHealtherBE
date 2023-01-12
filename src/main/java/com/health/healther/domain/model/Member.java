package com.health.healther.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.health.healther.constant.LoginType;
import com.health.healther.constant.MemberStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE MEMBER SET MEMBER.DELETED_AT = CURRENT_TIMESTAMP WHERE MEMBER.MEMBER_ID = ?")
@Getter
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NICKNAME")
	private String nickName;

	@Column(name = "PHONE")
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(name = "MEMBER_STATUS")
	private MemberStatus memberStatus;

	@Column(name = "OAUTH_ID")
	private String oauthId;

	@Enumerated(EnumType.STRING)
	@Column(name = "LOGIN_TYPE")
	private LoginType loginType;
}
