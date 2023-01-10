package com.health.healther.coupon.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COUPON_ID")
	private Long id;

	// @ManyToOne
	// @JoinColumn(name = "SPACE_ID")
	// private Space space;

	// @ManyToOne
	// @JoinColumn(name = "MEMBER_ID")
	// private Member member;

	@Column(name = "DISCOUNT_AMOUNT")
	private int discountAmount;

	@Column(name = "EXPIRED_DATE")
	private LocalDate expiredDate;

	@Column(name = "OPEN_DATE")
	private LocalDate openDate;

	@Column(name = "COUPON_NUMBER")
	private String couponNumber;

	@Column(name = "COUPON_COUNT")
	private int couponCount;

	@Column(name = "IS_USED")
	private boolean isUsed;
}
