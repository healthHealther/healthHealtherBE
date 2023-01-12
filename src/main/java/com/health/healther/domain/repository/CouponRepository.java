package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
