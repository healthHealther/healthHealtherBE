package com.health.healther.domain.model;

import java.time.LocalDate;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE COUPON SET COUPON.DELETED_AT = CURRENT_TIMESTAMP WHERE COUPON.COUPON_ID = ?")
@AllArgsConstructor
@Entity
public class Coupon extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COUPON_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@Column(name = "DISCOUNT_AMOUNT")
	private int discountAmount;

	@Column(name = "OPEN_DATE")
	private LocalDate openDate;

	@Column(name = "EXPIRED_DATE")
	private LocalDate expiredDate;

	@Column(name = "COUPON_NUMBER")
	private String couponNumber;

	@Column(name = "IS_USED")
	private boolean isUsed;

	public void updateCoupon(int discountAmount, LocalDate openDate, LocalDate expiredDate) {
		this.discountAmount = discountAmount;
		this.openDate = openDate;
		this.expiredDate = expiredDate;
	}

	public void useCoupon(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public void downloadCoupon(Member member) {
		this.member = member;
	}
}
