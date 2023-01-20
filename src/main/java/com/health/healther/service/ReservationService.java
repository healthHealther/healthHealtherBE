package com.health.healther.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.health.healther.domain.model.Coupon;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Reservation;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.model.SpaceTime;
import com.health.healther.domain.repository.CouponRepository;
import com.health.healther.domain.repository.ReservationRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.domain.repository.SpaceTimeRepository;
import com.health.healther.dto.reservation.MakeReservationRequestDto;
import com.health.healther.exception.reservation.AlreadyReservedException;
import com.health.healther.exception.reservation.InappropriateDateException;
import com.health.healther.exception.space.NotFoundSpaceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final SpaceTimeRepository spaceTimeRepository;
	private final SpaceRepository spaceRepository;
	private final MemberService memberService;
	private final CouponRepository couponRepository;

	public Reservation makeReservation(MakeReservationRequestDto form) {
		Member member = memberService.findUserFromToken();
		Space space = spaceRepository.findById(form.getSpaceId())
			.orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));
		//TODO memberId와 spaceId에 대한 Optional<Coupon> 불러오기
		//TODO Coupon 존재시 discountAmount 차액으로 가격 생성하기
		Reservation reservation = Reservation.builder()
			.member(member)
			.space(space)
			// .coupon()
			.reservationDate(getLocalDateFromString(form.getReservationDate()))
			.reservationTime(getReservationTime(form, space))
			// .price()
			.build();
		return reservationRepository.save(reservation);
	}

	public List<Integer> getAvailableTime(Long spaceId, String strDate) {
		List<Integer> allTime = new ArrayList<>();
		SpaceTime spaceTime = spaceTimeRepository.findBySpaceId(spaceId)
			.orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));
		LocalDate reserveDate = getLocalDateFromString(strDate);
		int openTime = spaceTime.getOpenTime();
		int closeTime = spaceTime.getCloseTime();
		for (int i = openTime; i < closeTime; i++) {
			Optional<Reservation> optionalReservation = reservationRepository.findByReservationDateAndReservationTime(
				reserveDate, i);
			if (optionalReservation.isEmpty()) {
				allTime.add(i++);
			}
		}
		return allTime;
	}

	private int getReservationTime(MakeReservationRequestDto form, Space space) {
		List<Integer> availableTimes = getAvailableTime(space.getId(), form.getReservationDate());
		int reservationTime = form.getReservationTime();
		if (availableTimes.contains(reservationTime)) {
			throw new AlreadyReservedException("이미 예약된 시간입니다.");
		}
		return reservationTime;
	}

	private LocalDate getLocalDateFromString(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(strDate, formatter);
		if (LocalDate.now().isBefore(date)) {
			throw new InappropriateDateException("예약 날짜는 현재 날짜보다 이후여야 합니다.");
		}
		return date;
	}

	private String localDateToString(LocalDate date) {
		return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
}