package com.health.healther.coupon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.coupon.domain.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
