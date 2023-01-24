package com.health.healther.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Reservation;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.model.SpaceTime;
import com.health.healther.domain.repository.CouponRepository;
import com.health.healther.domain.repository.ReservationRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.domain.repository.SpaceTimeRepository;
import com.health.healther.dto.reservation.AvailableTimeResponseDto;
import com.health.healther.dto.reservation.MakeReservationRequestDto;
import com.health.healther.dto.reservation.ReservationListResponseDto;
import com.health.healther.exception.reservation.AlreadyReservedException;
import com.health.healther.exception.reservation.InappropriateDateException;
import com.health.healther.exception.reservation.NotBusinessHoursException;
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

	public Long makeReservation(MakeReservationRequestDto form) {
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
			.reservationTime(validateAndGetReservationTime(form, space))
			// .price()
			.build();
		reservationRepository.save(reservation);
		return reservation.getId();
	}

	public Map<LocalDate, List<ReservationListResponseDto>> getReservations() {
		Member member = memberService.findUserFromToken();
		Map<LocalDate, List<ReservationListResponseDto>> map = new TreeMap<>();
		List<Reservation> reservationList =
			reservationRepository.findByMember_IdOrderByReservationDate(member.getId());
		if (reservationList.size() == 0) {
			return null;
		}
		for (Reservation reservation : reservationList) {
			LocalDate reservationDate = reservation.getReservationDate();
			if (LocalDate.now().minusDays(1).isAfter(reservationDate)
				|| map.containsKey(reservationDate)) {
				continue;
			}
			List<Reservation> reservations = reservationRepository.findAllByMember_IdAndReservationDateOrderByReservationTime(
				member.getId(),
				reservationDate
			);
			List<ReservationListResponseDto> reservationListResponseDtos = reservations
				.stream()
				.map(ReservationListResponseDto::from)
				.collect(Collectors.toList());
			map.put(reservationDate, reservationListResponseDtos);
		}
		return map;
	}

	public List<AvailableTimeResponseDto> getAvailableTimeResponseDto(Long spaceId, String strDate) {
		return getAvailableTime(spaceId, strDate).stream()
			.map(AvailableTimeResponseDto::from)
			.collect(Collectors.toList());
	}

	private List<Integer> getAvailableTime(Long spaceId, String strDate) {
		List<Integer> allTime = new ArrayList<>();
		SpaceTime spaceTime = spaceTimeRepository.findBySpaceId(spaceId)
			.orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));
		LocalDate reserveDate = getLocalDateFromString(strDate);
		int openTime = spaceTime.getOpenTime();
		int closeTime = spaceTime.getCloseTime();
		for (int i = openTime; i < closeTime; i++) {
			Optional<Reservation> optionalReservation =
				reservationRepository.findByReservationDateAndReservationTime(reserveDate, i);
			if (optionalReservation.isEmpty()) {
				allTime.add(i);
			}
		}
		Collections.sort(allTime);
		return allTime;
	}

	private int validateAndGetReservationTime(MakeReservationRequestDto form, Space space) {
		List<Integer> availableTimes = getAvailableTime(space.getId(), form.getReservationDate());
		int reservationTime = form.getReservationTime();
		int minTime = space.getSpaceTime().getOpenTime();
		int maxTime = space.getSpaceTime().getCloseTime();
		if (reservationTime < minTime || reservationTime > maxTime) {
			throw new NotBusinessHoursException("영업시간이 아닙니다.");
		} else if (!availableTimes.contains(reservationTime)) {
			throw new AlreadyReservedException("이미 예약된 시간입니다.");
		}
		return reservationTime;
	}

	private LocalDate getLocalDateFromString(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(strDate, formatter);
		if (LocalDate.now().isAfter(date)) {
			throw new InappropriateDateException("예약 날짜는 현재 날짜보다 이후여야 합니다.");
		} else if (LocalDate.now().equals(date)) {
			throw new InappropriateDateException("당일 예약은 불가능 합니다.");
		}
		return date;
	}
}
