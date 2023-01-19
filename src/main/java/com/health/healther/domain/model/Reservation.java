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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE RESERVATION SET RESERVATION.DELETED_AT = CURRENT_TIMESTAMP WHERE RESERVATION.RESERVATION_ID = ?")
@Entity
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RESERVATION_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_ID")
	private Coupon coupon;

	@Column(name = "RESERVATION_DATE")
	private LocalDate reservationDate;

	@Column(name = "RESERVATION_TIME")
	private int reservationTime;

	@Column(name = "PRICE")
	private int price;
}
