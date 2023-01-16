package com.health.healther.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Optional<Coupon> findBySpace_Id(Long spaceId);

}
